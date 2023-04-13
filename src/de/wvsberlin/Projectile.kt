package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGBitmap
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import java.lang.IllegalArgumentException
import kotlin.math.pow

typealias ProjectileSupplier = (game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) -> Projectile

class Projectile(
        val game: Game,
        val key: Int,
        val richtungsvektor: Vektor,
        sprite: BufferedImage,
        var lifetime: Int,
        val pos: MutableVektor,
        var pierce: Int = 1,
        size: Number = 16,
        val damage: Double = 1.0
) : Actor(false, sprite) {
    var enemiesHit: List<Enemy> = emptyList()
    val size: Double

    init {
        this.size = size.toDouble()
        if (lifetime < 0) throw IllegalArgumentException("Lifetime must not be below zero!")
        if (pierce < 0) throw IllegalArgumentException("Pierce must not be below zero!")
        if (this.size < 0) throw IllegalArgumentException("Size must not be below zero!")
        direction = richtungsvektor.getAngle()
    }

    companion object {
        @JvmStatic
        fun EXAMPLE_PROJ(game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) = Projectile(
                game = game,
                key = key,
                richtungsvektor = Vektor.fromAngleAndMagnitude(direction, 5),
                sprite = Sprite.SPRITE,
                lifetime = lifetime,
                pierce = pierce,
                size = 6,
                pos = pos,
                damage = damage
        )
        @JvmStatic
        fun TOWER1_PROJ(game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) = Projectile(
                game = game,
                key = key,
                richtungsvektor = Vektor.fromAngleAndMagnitude(direction, 5),
                sprite = GGBitmap.getScaledImage(Sprite.ARROW, 1.0, direction - 90),
                lifetime = lifetime,
                pierce = pierce,
                size = 6,
                pos = pos,
                damage = damage
        )
        @JvmStatic
        fun TOWER2_PROJ(game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) = Projectile(
                game = game,
                key = key,
                richtungsvektor = Vektor.fromAngleAndMagnitude(direction, 5),
                sprite = Sprite.UWU,
                lifetime = lifetime,
                pierce = pierce,
                size = 6,
                pos = pos,
                damage = damage
        )
        @JvmStatic
        fun TOWER3_PROJ(game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) = Projectile(
                game = game,
                key = key,
                richtungsvektor = Vektor.fromAngleAndMagnitude(direction, 5),
                sprite = Sprite.SPRITE,
                lifetime = lifetime,
                pierce = pierce,
                size = 6,
                pos = pos,
                damage = damage
        )
    }

    fun tick() {
        if ((lifetime <= 0) or (pierce <= 0)) {
            despawn()
            return
        }

        lifetime--
        pos += richtungsvektor
        location = pos.toLocation()

        for (enemy in game.activeEnemies.values) {
            if (!isTouchingEnemy(enemy))
                continue

            if (pierce <= 0) {
                despawn()
                return
            }

            onEnemyTouched(enemy)
        }
    }

    /**
     * Removes projectile from all the places it could be.
     */
    fun despawn() = game.grid.removeActor(game.activeProjectiles.remove(key))  // glorious oneliner

    fun isTouchingEnemy(enemy: Enemy): Boolean =
            (Vektor.dist(pos, enemy.pos, false) < (size + enemy.size).pow(2)) and (enemy !in enemiesHit)

    fun onEnemyTouched(enemy: Enemy) {
        enemy.health -= damage
        pierce--
    }

    override fun getDirection(): Double = richtungsvektor.getAngle()
}