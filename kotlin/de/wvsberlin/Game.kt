package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GameGrid
import de.wvsberlin.vektor.Vektor

class Game(val menu: Menu, difficulty: Difficulty, val gameMap: GameMap) {
    var health: Int
    var money: Double
    var paused = true
    val grid: GameGrid = menu.gamegrid
    var currentRound = -1
    var roundActive = false
    val debug: Boolean
        get() = menu.bDebug.isSelected
    var heldTower: HeldTower? = null
    var selectedTower: Tower? = null

    val enemyKeyGen = Counter()
    val projectileKeyGen = Counter()
    val towerKeyGen = Counter()

    val activeEnemies = HashMap<Int, Enemy>()
    val activeProjectiles = HashMap<Int, Projectile>()
    val activeTowers = HashMap<Int, Tower>()

    init {
        when (difficulty) {
            Difficulty.EASY -> {
                health = 100
                money = 1000.0
            }

            Difficulty.NORMAL -> {
                health = 50
                money = 500.0
            }

            Difficulty.HARD -> {
                health = 1
                money = 250.0
            }
        }
    }

    fun spawnProjectile(
            pos: Vektor,
            direction: Double,
            projSupplier: ProjectileSupplier,
            lifetime: Int,
            pierce: Int
    ): Projectile {
        TODO()
    }

    fun spawnEnemy(
            enemySupplier: EnemySupplier,
            segmentIdx: Int = 0,
            segmentProgress: Double = 0.0
    ): Enemy {
        TODO()
    }

    fun placeHeldTower(pos: Vektor) {
        TODO()
    }

    fun sellSelectedTower() {
        TODO()
    }

    fun removeAllActors() {
        TODO()
    }

    fun startNextRound() {
        TODO()
    }

    fun updateMoney(difference: Number) {
        money += difference.toDouble()
        menu.tMoney.text = money.toString()
    }

    fun updateCost() {
        TODO()
    }

    fun tick() {
        TODO()
    }

    fun gameOver() {
        TODO()
    }
}

private class TickActor(val game: Game) : Actor() {
    /**
     * Diese Klasse hat den Zweck, die Tick-Function des Games an die Tickrate des Gamegrids zu binden.
     * Ich habe wirklich keinen Bock, Game von Actor erben zu lassen...
     */
    override fun act() = game.tick()
}
