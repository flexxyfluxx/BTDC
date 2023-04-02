package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGBitmap
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage

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
        val childCount: Int = 0,
        val childSpacing: Int = 0,
        size: Number = 16,
        reward: Number = 1
) : Actor(sprite) {
    val pathNodes: MutableList<Vektor>
    var prevNodeToNextNodeVektor: Vektor
    var currentSegmentLength: Double
    var currentSegmentUnitVektor: Vektor
    var currentSegmentRichtungsVektor: Vektor
    var segmentProgress = segmentProgress.toDouble()
    var health = health.toDouble()
    val size = size.toDouble()
    val reward = reward.toDouble()
    var pos: MutableVektor

    init {
        pathNodes = game.gameMap.pathNodes
        prevNodeToNextNodeVektor = pathNodes[segmentIdx + 1] - pathNodes[segmentIdx]
        currentSegmentLength = prevNodeToNextNodeVektor.abs()
        currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        currentSegmentRichtungsVektor = currentSegmentUnitVektor * speed

        pos = MutableVektor.fromImmutable(pathNodes[segmentIdx] + currentSegmentUnitVektor * segmentProgress)
    }

    fun tick() {
        if (health <= 0) die(-health)

        segmentProgress += speed

        if (segmentProgress >= currentSegmentLength) {
            val overshoot = segmentProgress - currentSegmentLength
            nextSegment()
            pos = MutableVektor.fromImmutable(pathNodes[segmentIdx] + currentSegmentUnitVektor * overshoot)
            location = pos.toLocation()
        }
    }

    fun despawn() {
        game.grid.removeActor(this)
        game.activeEnemies.remove(key)
    }

    fun die(overshoot: Double) {
        if (game.debug) println("Enemy $key died.")

        despawn()
        game.updateMoney(reward)

        childSupplier ?: return

        val child = game.spawnEnemy(childSupplier, segmentIdx, segmentProgress)
        if (overshoot < child.health) {
            child.health -= overshoot
            return
        }
        child.die(overshoot - child.health)
    }

    fun nextSegment() {
        segmentIdx++

        if (segmentIdx > pathNodes.lastIndex - 1) {
            game.health -= dmg
            if (game.health <= 0) {
                game.health = 0
                game.gameOver()
            }
            game.menu.tHealth.text = game.health.toString()
            despawn()
            return
        }

        prevNodeToNextNodeVektor = pathNodes[segmentIdx + 1] - pathNodes[segmentIdx]
        currentSegmentLength = prevNodeToNextNodeVektor.abs()
        currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        currentSegmentRichtungsVektor = currentSegmentUnitVektor * speed
        segmentProgress = 0.0
    }

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
                sprite = Sprite.SPRITE,
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
                sprite = Sprite.SPRITE,
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
                sprite = Sprite.SPRITE,
                childSupplier = this::YELLOW
        )
    }
}