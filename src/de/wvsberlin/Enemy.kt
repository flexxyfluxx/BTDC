package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGBitmap
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import kotlin.contracts.contract

typealias EnemySupplier = (game: Game, key: Int, segmentIdx: Int, segmentProgress: Double) -> Enemy

class Enemy(
        val game: Game,
        val key: Int,
        var dmg: Int,
        health: Number,
        val speed: Double,
        sprite: BufferedImage,
        var segmentIdx: Int,
        segmentProgress: Number,
        val childSupplier: EnemySupplier? = null,
        val childCount: Int = 1,
        val childSpacing: Int = 20,
        size: Number = 16,
        val reward: Int = 1
) : Actor(sprite) {
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
    }

    /**
     * Runs every tick.
     *
     * Implements per-tick logic of enemy.
     */
    fun tick() {
        // die if appropriate.
        if (health <= 0)
            die()

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
    fun despawn() {
        game.grid.removeActor(this)
        game.activeEnemies.remove(key)
    }

    /**
     * Mark the enemy for removal from the system to avoid concurrency issues when iterating over the same hashmap you
     * want to eventually rm enemies from.
     */
    fun markForGC() {
        game.enemiesToGC.add(key)
    }

    /**
     * Handles the death of enemies (not their gc! that would be Enemy.despawn)
     */
    fun die() {
        if (game.debug) println("Enemy $key died.")

        val overshoot = -health

        // gc enemy and award reward
        markForGC()
        game.updateMoney(reward)

        childSupplier ?: return
        // if we do have a childSupplier (!= null), spawn a child.
        for (c in 0 until childCount) {
            // spawn children as needed, staggered toward the back of the track
            val child = game.spawnEnemy(childSupplier, segmentIdx, segmentProgress - childSpacing * c)

            child.health -= overshoot
            if (child.health <= 0) {
                // if the child would die from the leftover dmg, act like it.
                child.die()
            }
        }
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
            game.health -= dmg
            game.menu.tHealth.text = game.health.toString()
            markForGC()
            return
        }

        prevNodeToNextNodeVektor = pathNodes[segmentIdx + 1] - pathNodes[segmentIdx]
        currentSegmentLength = prevNodeToNextNodeVektor.abs()
        currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        currentSegmentRichtungsVektor = currentSegmentUnitVektor * speed
        segmentProgress = overshoot

        pos = MutableVektor.fromImmutable(pathNodes[segmentIdx] + currentSegmentUnitVektor * overshoot)
    }

    /**
     * All the enemy suppliers.
     *
     * Due to how the Enemy class is laid out, the suppliers are essentially a sort of wrapper around the primary constructor,
     * and provide some templates for enemies that can be accessed and used easily.
     *
     * Naming of enemy suppliers is analogous to BTD6 counterparts.
     */
    companion object {
        @JvmStatic
        fun WEAKEST(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy = Enemy(
                game = game,
                key = key,
                segmentIdx = segmentIdx,
                segmentProgress = segmentProgress,
                dmg = 1,
                health = 1,
                speed = 1.5,
                sprite = Sprite.BALLOON_WEAKEST,
        )

        @JvmStatic
        fun BLUE(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy = Enemy(
                game = game,
                key = key,
                segmentIdx = segmentIdx,
                segmentProgress = segmentProgress,
                dmg = 2,
                health = 1,
                speed = 2.0,
                sprite = Sprite.BALLOON_BLUE,
                childSupplier = this::WEAKEST
        )

        @JvmStatic
        fun GREEN(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy = Enemy(
                game = game,
                key = key,
                segmentIdx = segmentIdx,
                segmentProgress = segmentProgress,
                dmg = 3,
                health = 1,
                speed = 3.0,
                sprite = Sprite.SPRITE,  // TODO add real sprite
                childSupplier = this::BLUE
        )

        @JvmStatic
        fun YELLOW(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy = Enemy(
                game = game,
                key = key,
                segmentIdx = segmentIdx,
                segmentProgress = segmentProgress,
                dmg = 4,
                health = 1,
                speed = 5.0,
                sprite = Sprite.SPRITE,  // TODO add real sprite
                childSupplier = this::GREEN
        )

        @JvmStatic
        fun PINK(game: Game, key: Int, segmentIdx: Int, segmentProgress: Double): Enemy = Enemy(
                game = game,
                key = key,
                segmentIdx = segmentIdx,
                segmentProgress = segmentProgress,
                dmg = 5,
                health = 1,
                speed = 8.0,
                sprite = Sprite.SPRITE,  // TODO add real sprite
                childSupplier = this::YELLOW
        )
    }
}