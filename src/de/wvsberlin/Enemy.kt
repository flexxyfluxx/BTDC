package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage

interface DynamicEnemy {
    fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy
    val initialHealth: Double
        get() = 1.0
    val reward: Int
        get() = 1
    val children: Array<DynamicEnemy>
        get() = emptyArray()
    val childSpacing: Double
        get() = 50.0
}

class EnemyChildEntry(vararg val children: DynamicEnemy, val spacing: Double = 50.0)

fun spawnChildren(enemy: DynamicEnemy, overkill: Double, game: Game, segmentIdx: Int, segmentProgress: Double, debug: Boolean) {
    for (childElem in enemy.children.withIndex()) {
        val childType = childElem.value
        val c = childElem.index
        val appropriatedSegmentProgress = segmentProgress - enemy.childSpacing * c

        if (overkill < childType.initialHealth) {
            val newchild = game.spawnEnemy(childType, segmentIdx, appropriatedSegmentProgress)
            newchild.health -= overkill
            continue
        }

        game.updateMoney(childType.reward)
        spawnChildren(childType, overkill - childType.initialHealth, game, segmentIdx, appropriatedSegmentProgress, debug)
    }
}

abstract class Enemy(
        val game: Game,
        val key: Int,
        val dmg: Int,
        health: Number,
        val speed: Double,
        sprite: BufferedImage,
        var segmentIdx: Int,
        segmentProgress: Number,
        size: Number = 16,
        val rotates: Boolean = false,
        val reward: Int = 1
) : Actor(rotates, sprite) {
    abstract val enemyCompanion: DynamicEnemy
    var prevNodeToNextNodeVektor: Vektor
    var currentSegmentLength: Double
    var currentSegmentUnitVektor: Vektor
    var currentSegmentRichtungsVektor: Vektor
    var pos: MutableVektor

    var segmentProgress = segmentProgress.toDouble()
    var health = health.toDouble()
    val size = size.toDouble()

    val pathNodes: MutableList<Vektor> = game.gameMap.pathNodes

    init {
        prevNodeToNextNodeVektor = pathNodes[segmentIdx + 1] - pathNodes[segmentIdx]
        currentSegmentLength = prevNodeToNextNodeVektor.abs()
        currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        currentSegmentRichtungsVektor = currentSegmentUnitVektor * speed

        pos = MutableVektor.fromImmutable(pathNodes[segmentIdx] + currentSegmentUnitVektor * segmentProgress)
        direction = currentSegmentUnitVektor.getAngle()
    }

    /**
     * Runs every tick.
     *
     * Implements per-tick logic of enemy.
     */
    fun tick() {
        // die if appropriate.
        if (health <= 0) {
            die()
            return
        }

        // and then just handle forward movement of the enemy on the track.

        segmentProgress += speed

        if (segmentProgress >= currentSegmentLength)
            // if we hit/pass the end of the current segment, make sure the enemy arrives in the next segment properly.
            nextSegment()
        else
            pos += currentSegmentRichtungsVektor

        // if we don't hit end of segment, just move forward normally.
        location = pos.toLocation()
    }

    /**
     * Handles the correct deinitialization of an enemy. Just making sure no spare references to it remain
     * and making sure it can be safely gc'd etc.
     *
     * Not to be confused with Enemy.die, which handles the killing logic of enemies.
     */
    fun despawn() = game.grid.removeActor(game.activeEnemies.remove(key))  // glorious oneliner

    /**
     * Handles the death of enemies (not their gc! that would be Enemy.despawn)
     */
    fun die() {
        if (game.debug) println("Enemy $key died.")

        game.updateMoney(reward)

        spawnChildren(enemyCompanion, -health, game, segmentIdx, segmentProgress, game.debug)
        despawn()
    }

    /**
     * Handles correct transitioning of enemy to next segment on map.
     */
    fun nextSegment() {
        // basically, overshoot is just how far the enemy has already travelled on the next segment.. that is literally all.
        val overshoot = segmentProgress - currentSegmentLength

        segmentIdx++

        if (segmentIdx > pathNodes.lastIndex - 1) {
            // if the enemy reaches/surpasses the end,
            // (not that surpassing should be possible, but it might save me a headache later lol)
            // deal the enemy's damage and gc the enemy (it no longer exists)
            game.updateHealth(-dmg)
            despawn()
            return
        }

        prevNodeToNextNodeVektor = pathNodes[segmentIdx + 1] - pathNodes[segmentIdx]
        currentSegmentLength = prevNodeToNextNodeVektor.abs()
        currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        currentSegmentRichtungsVektor = currentSegmentUnitVektor * speed
        segmentProgress = overshoot

        pos = MutableVektor.fromImmutable(pathNodes[segmentIdx] + currentSegmentUnitVektor * overshoot)
        if (rotates)
            direction = currentSegmentUnitVektor.getAngle()
    }

    ////////////////////////////////////////////////////////////////
    // Please don't ask me to explain this.
    ////////////////////////////////////////////////////////////////

    object WEAKEST : DynamicEnemy {
        override fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Weakest =
                Weakest(game, key, segmentIdx, segmentProgress)
    }

    object BLUE : DynamicEnemy {
        override fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Blue =
                Blue(game, key, segmentIdx, segmentProgress)

        override val children = arrayOf<DynamicEnemy>(WEAKEST)
    }

    object GREEN : DynamicEnemy {
        override fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Green =
                Green(game, key, segmentIdx, segmentProgress)

        override val children = arrayOf<DynamicEnemy>(BLUE)
    }

    object YELLOW : DynamicEnemy {
        override fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Yellow =
                Yellow(game, key, segmentIdx, segmentProgress)

        override val children = arrayOf<DynamicEnemy>(GREEN)
    }

    object PINK : DynamicEnemy {
        override fun new(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Pink =
                Pink(game, key, segmentIdx, segmentProgress)

        override val children = arrayOf<DynamicEnemy>(YELLOW)
    }

    ////////////////////////////////////////////////////////////////
    // Now, the Enemy classes.
    // These used to just be suppliers that returned base Enemies
    // with certain params, but that is probably suboptimal.
    ////////////////////////////////////////////////////////////////

    class Weakest(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) : Enemy(
            game = game, key = key, segmentIdx = segmentIdx, segmentProgress = segmentProgress,
            dmg = 1, health = 1, speed = 1.5, sprite = Sprite.BALLOON_WEAKEST
    ) {
        override val enemyCompanion = WEAKEST
    }

    class Blue(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) : Enemy(
            game = game, key = key, segmentIdx = segmentIdx, segmentProgress = segmentProgress,
            dmg = 2, health = 1, speed = 2.0, sprite = Sprite.BALLOON_BLUE
    ) {
        override val enemyCompanion = BLUE
    }

    class Green(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) : Enemy(
            game = game, key = key, segmentIdx = segmentIdx, segmentProgress = segmentProgress,
            dmg = 3, health = 1, speed = 3.0, sprite = Sprite.SPRITE
    ) {
        override val enemyCompanion = GREEN
    }

    class Yellow(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) : Enemy(
            game = game, key = key, segmentIdx = segmentIdx, segmentProgress = segmentProgress,
            dmg = 4, health = 1, speed = 5.0, sprite = Sprite.SPRITE
    ) {
        override val enemyCompanion = YELLOW
    }

    class Pink(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) : Enemy(
            game = game, key = key, segmentIdx = segmentIdx, segmentProgress = segmentProgress,
            dmg = 5, health = 1, speed = 8.0, sprite = Sprite.SPRITE
    ) {
        override val enemyCompanion = PINK
    }
}