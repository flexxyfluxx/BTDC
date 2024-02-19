package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGBitmap
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import java.lang.IllegalArgumentException
import kotlin.math.pow

typealias ProjectileSupplier = (game: Game, key: Int, direction: Double, pos: MutableVektor, lifetime: Int, pierce: Int, damage: Double) -> Projectile

interface DynamicProjectile {
    fun new(game: Game, key: Int, direction: Double, pos: MutableVektor, pierce: Int, damage: Double): Projectile =
            Projectile(game, key, Vektor.fromAngleAndMagnitude(direction, speed), sprite, lifetime, pos, pierce, size, damage)
    val size: Double
        get() = 6.0
    val sprite: BufferedImage
        get() = Sprite.SPRITE
    val speed: Double
        get() = 10.0
    val lifetime: Int
        get() = 100
}

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
) : Actor(true, sprite) {
    var enemiesHit: List<Enemy> = emptyList()
    val size: Double

    init {
        val sizeDouble = size.toDouble()
        if (lifetime < 0) this.lifetime = 0
        if (pierce < 0) this.pierce = 0

        this.size = if (sizeDouble < 0) {
            0.0
        } else {
            sizeDouble
        }
        direction = richtungsvektor.getAngle()
        super.setDirection(direction)
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

    object EXAMPLE_PROJ : DynamicProjectile

    object TOWER1_PROJ : DynamicProjectile {
        fun new(game: Game, key: Int, direction: Double, pos: MutableVektor, pierce: Int, damage: Double, lifetime: Int): Projectile =
                Projectile(game, key, Vektor.fromAngleAndMagnitude(direction, speed),
                        sprite = GGBitmap.getScaledImage(sprite, 1.0, 90 - direction),
                        lifetime = lifetime, pos = pos, pierce = pierce, damage = damage, size = size)

        override val sprite = Sprite.ARROW
    }

    object TOWER2_PROJ : DynamicProjectile {
        override val sprite = Sprite.UWU
    }

    object TOWER3_PROJ : DynamicProjectile
}