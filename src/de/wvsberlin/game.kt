package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGMouse
import ch.aplu.jgamegrid.GameGrid
import ch.aplu.jgamegrid.Location
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.Point
import java.lang.IllegalArgumentException

class Game(val menu: Menu, difficulty: Difficulty, val gameMap: GameMap) {
    var health: Int
    var money: Int
    var paused = true
    var currentRound = -1
    var roundActive = false
    val debug: Boolean = menu.bDebug.isSelected
    var heldTower: HeldTower? = null
    var selectedTower: Tower? = null

    val grid: GameGrid = menu.gamegrid

    val rounds = if (debug) {
        Round.ROUNDS_DEBUG(this)
    } else {
        Round.ROUNDS(this)
    }

    val enemyKeyGen = Counter()
    val activeEnemies = HashMap<Int, Enemy>()
    var enemiesToGC = mutableListOf<Int>()
    val projectileKeyGen = Counter()
    val activeProjectiles = HashMap<Int, Projectile>()
    var projectilesToGC = mutableListOf<Int>()
    val towerKeyGen = Counter()
    val activeTowers = HashMap<Int, Tower>()
    // we do not need a list of towers to GC, since this is not done while iterating through the hashmap.

    val tickActor = TickActor(this)

    init {
        when (difficulty) {
            Difficulty.EASY -> {
                health = 100
                money = 1000
            }

            Difficulty.NORMAL -> {
                health = 50
                money = 500
            }

            Difficulty.HARD -> {
                health = 1
                money = 250
            }
        }

        menu.tMoney.text = money.toString()
        menu.tHealth.text = health.toString()
        menu.tCurrentRound.text = (currentRound+1).toString()

        menu.lTower1.text = Tower1.cost.toString()
        menu.lTower2.text = Tower2.cost.toString()
        menu.lTower3.text = if (debug) {
            TowerDebug.cost.toString()
        } else {
            Tower3.cost.toString()
        }
        menu.lTower4.text = "0"

        gameMap.setBgOfGrid(grid, debug)

        grid.setSimulationPeriod(20)
        grid.addMouseListener(this::mouseLPress, GGMouse.lPress)
        grid.addMouseListener(this::mouseRPress, GGMouse.rPress)

        grid.addActor(tickActor, Location())
    }

    /**
     * Spawn yourself a brand new projectile in a place, with a direction.
     */
    fun spawnProjectile(
            pos: Vektor,
            direction: Double,
            projSupplier: ProjectileSupplier,
            lifetime: Int,
            pierce: Int,
            damage: Number
    ): Projectile {
        if (debug) println("[INFO] Projectile spawned at (${pos.x}, ${pos.y})")

        val key = projectileKeyGen.next()
        val newProjectile = projSupplier(this, key, direction, MutableVektor.fromImmutable(pos), lifetime, pierce, damage.toDouble())
        activeProjectiles[key] = newProjectile
        grid.addActor(newProjectile, pos.toLocation())
        newProjectile.show()
        return newProjectile
    }

    /**
     * Spawn yourself a brand new enemy at a specified location on the track.
     */
    fun spawnEnemy(enemySupplier: EnemySupplier, segmentIdx: Int = 0, segmentProgress: Double = 0.0): Enemy {
        if (debug) println("[INFO] Enemy spawned at segment $segmentIdx with progress $segmentProgress")

        val key = enemyKeyGen.next()
        val newEnemy = enemySupplier(this, key, segmentIdx, segmentProgress)
        activeEnemies[key] = newEnemy
        grid.addActor(newEnemy, (gameMap.pathNodes[segmentIdx] + newEnemy.currentSegmentUnitVektor * segmentProgress).toLocation())
        return newEnemy
    }

    fun getNearestTower(pos: Vektor, selectedTower: Tower? = null): Tower? {
        if (debug) println("Start finding nearest tower...")

        var nearestTower: Tower? = null
        var currentDist = 9999.0

        for (tower in activeTowers.values) {
            if ((tower === selectedTower)) continue

            if (nearestTower == null) {
                nearestTower = tower
                currentDist = tower.pos..pos
                if (debug) println("Initial nearest tower with dist: $")
                continue
            }

            val nextDist = tower.pos..pos
            if (nextDist < currentDist) {
                if (debug) println("New nearest tower with dist: $nextDist")
                nearestTower = tower
                currentDist = nextDist
            }
        }
        if (debug) {
            if (nearestTower == null) {
                println("No other tower found.")
            } else {
                println("Closest tower has dist $currentDist")
            }
        }
        return nearestTower
    }

    fun getDistToPath(pos: Vektor): Double {
        if (debug) println("Start finding distance to path...")

        var dist: Double = 99999.0  // such a large distance cannot reasonably occur.

        var node1: Vektor
        var node2: Vektor
        var clampedDist: Double

        for (c in 0 until gameMap.pathNodes.lastIndex) {
            // iterate through all the pairs of connected nodes; these are the segments.
            // find the smallest dist to any one of the segments.
            node1 = gameMap.pathNodes[c]
            node2 = gameMap.pathNodes[c+1]
            clampedDist = Vektor.clampedDist(node1, node2, pos)
            if (debug) println("clampedDist = $clampedDist")

            if (clampedDist < dist)
                dist = clampedDist
        }

        if (debug) println("final dist to path: $dist")

        return dist
    }

    fun placeHeldTower(pos: Vektor) {
        if (debug) println("Game.placeHeldTower")

        if (heldTower == null) {
            if (debug) println("[INFO] heldTower is null; aborting.")
            return
        }
        // assert that the heldTower is, in fact, not null, after making sure it isn't
        val heldTower = heldTower!!

        val key = towerKeyGen.next()
        val newTower = heldTower.getTower(pos, key, this)

        if (getDistToPath(pos) < gameMap.pathWidth + newTower.sizeRadius) {
            // if dist to path is too small, show angry red X sprite at click pos and wait for new click
            heldTower.pos = pos
            heldTower.location = pos.toLocation()
            heldTower.show(1)
            return
        }

        val nearestTower = getNearestTower(pos)
        if (nearestTower != null) {
            if (nearestTower.pos..pos < nearestTower.sizeRadius + newTower.sizeRadius) {
                heldTower.pos = pos
                heldTower.location = pos.toLocation()
                heldTower.show(1)
                return
            }
        }

        grid.removeActor(heldTower)

        activeTowers[key] = newTower
        grid.addActor(newTower, pos.toLocation())
        newTower.addMouseTouchListener(this::selectTower, GGMouse.lPress)
        this.heldTower = null
    }

    fun sellSelectedTower() {
        // assert that selectedTower is not null
        selectedTower ?: return
        val selectedTower = selectedTower!!

        updateMoney(selectedTower.cost.floorDiv(2))
        grid.removeActor(selectedTower)
        activeTowers.remove(selectedTower.key)
        this.selectedTower = null
        updateCost()
    }

    fun changeTowerTarget(pos: Vektor) {
        //assert that selectedTower is not null
        selectedTower ?: return
        val selectedTower = selectedTower!!

        // for debug purposes
        val previousDirection = selectedTower.targetDirection

        val targetVektor = pos - selectedTower.pos
        // learned the hard way that clicking perfectly on the tower results in a ZeroDivisionError,
        // because Vektor(0,0).abs() == 0, obviously, and getUnitized divides by the result of that.
        if (targetVektor == Vektor.NullVektor) {
            if (debug) println("[INFO] Could not change target direction of tower ${selectedTower.key} because something something NullVektor.")

            this.selectedTower = null
            return
        }

        selectedTower.targetDirection = targetVektor.getAngle()
        if (debug) println("[INFO] Updated target direction of tower ${selectedTower.key} from $previousDirection to ${selectedTower.targetDirection}.")

        this.selectedTower = null
        updateCost()
    }

    fun selectTower(tower: Actor, mouse: GGMouse, pos: Point) {
        val towerButActually = tower as Tower
        if (debug) println("Tower ${tower.key} selected.")

        selectedTower = towerButActually
        updateCost()
    }

    fun mouseLPress(mouse: GGMouse): Boolean {
        val clickPos = Vektor(mouse.x, mouse.y)

        if (debug) println("[INFO] Mouse left click at (${clickPos.x}, ${clickPos.y})")

        if (heldTower != null) {
            placeHeldTower(clickPos)
            return true
        }
        else if (selectedTower != null) {
            changeTowerTarget(clickPos)
            return true
        }
        return false
    }
    fun mouseRPress(mouse: GGMouse): Boolean {
        selectedTower ?: return true
        val selectedTower = selectedTower!!

        val clickPos = Vektor(mouse.x, mouse.y)

        if (money < (selectedTower.cost.floorDiv(2)))
            return true

        if (getDistToPath(clickPos) < gameMap.pathWidth + selectedTower.sizeRadius)
            return true

        val nearestTower = getNearestTower(clickPos, selectedTower)
        if (nearestTower != null)
            if (nearestTower.pos..clickPos < nearestTower.sizeRadius + selectedTower.sizeRadius)
                return true


        selectedTower.pos = clickPos
        selectedTower.location = clickPos.toLocation()
        updateMoney(-(selectedTower.cost.floorDiv(2)))
        this.selectedTower = null
        updateCost()
        return true
    }

    fun removeAllActors() {
        if (heldTower != null) {
            grid.removeActor(heldTower)
            heldTower = null
        }
        selectedTower = null
        updateCost()

        for (tower in activeTowers.values)
            grid.removeActor(tower)
        for (enemy in activeEnemies.values)
            grid.removeActor(enemy)
        for (projectile in activeProjectiles.values)
            grid.removeActor(projectile)

        grid.removeActor(tickActor)
        grid.bg.clear()
    }

    /**
     * Increment the current round and win the game, if all rounds have been beaten.
     */
    fun startNextRound() {
        if (debug) println("[INFO] Round started.")

        // If we've surpassed the last round, win.
        if (currentRound > rounds.lastIndex) win()

        menu.tCurrentRound.text = (++currentRound + 1).toString()
        // man, isn't `++` just such concise incrementation syntax?
        paused = false
    }

    /**
     * Add money to bank.
     */
    fun updateMoney(difference: Int) {
        money += difference
        menu.tMoney.text = money.toString()
    }

    /**
     * Update the upgrade cost display according to the currently selected tower (or lack thereof).
     */
    fun updateCost() {
        if (selectedTower == null) {
            menu.tUpgrade1.text = ""
            menu.tUpgrade2.text = ""
            menu.tUpgrade3.text = ""
            menu.lUpgrade3.text = "Tower-specific"
            return
        }
        val selectedTower = selectedTower!!

        menu.tUpgrade1.text = selectedTower.attackSpeedUpgradeCost.toString()
        menu.tUpgrade2.text = selectedTower.attackDamageUpgradeCost.toString()
        menu.tUpgrade3.text = selectedTower.upgrade3Cost.toString()
        menu.lUpgrade3.text = selectedTower.upgrade3Text
    }

    /**
     * Runs every game tick.
     *
     * Implements per-tick game-level logic.
     */
    fun tick() {
        if (paused)
            return

        if (health <= 0) {
            gameOver()
            return
        }

        // Round.tick's return value indicates whether it's been depleted. Act on that.
        if (rounds[currentRound].tick()) {
            if (debug) println("[INFO] Round exhausted.")
            if (activeEnemies.isEmpty()) {
                if (debug) println("[INFO] Round ended.")

                if (currentRound >= rounds.lastIndex) {
                    if (debug) println("[INFO] Game won. Ending game.")
                    win()
                    return
                }

                // If no active enemies remain, add round reward to bank
                // and, if autostart is on, automatically start next round.
                updateMoney(rounds[currentRound].reward)
                // gc all active projectiles too, while we're at it. don't fancy frozen projectiles everywhere if the
                // game is paused between rounds
                projectilesToGC.addAll(activeProjectiles.keys)
                for (projID in projectilesToGC) activeProjectiles[projID]?.despawn()

                if (!menu.bAutostart.isSelected) {
                    paused = true
                    return
                }
                startNextRound()
            }
        }

        for (tower in activeTowers.values) tower.tick()
        for (projectile in activeProjectiles.values) projectile.tick()
        for (enemy in activeEnemies.values) enemy.tick()

        // after everyone has their fun, gc things as needed
        for (enemyID in enemiesToGC) activeEnemies[enemyID]?.despawn()
        enemiesToGC = mutableListOf()
        for (projID in projectilesToGC) activeProjectiles[projID]?.despawn()
        projectilesToGC = mutableListOf()
    }

    /**
     * When the game is lost, call this.
     */
    fun gameOver() {
        grid.doPause()
        menu.setCurrentScreen(4)
    }

    /**
     * When the game is won, call this.
     */
    fun win() {
        grid.doPause()
        menu.setCurrentScreen(5)
    }
}

/**
 * Diese Klasse hat den einzigen Zweck, die Tick-Function des Games an die Tickrate des Gamegrids zu binden.
 *
 * Ich habe nämlich wirklich keinen Bock, Game von Actor erben zu lassen...
 */
class TickActor(val game: Game) : Actor() {
    override fun act() = game.tick()
}
