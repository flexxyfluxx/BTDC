package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import de.wvsberlin.vektor.MutableVektor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import java.lang.IllegalArgumentException
import kotlin.math.pow

typealias ProjectileSupplier = (game: Game, key: Int, direction: Double, pos: Vektor, lifetime: Int, pierce: Int) -> Projectile

// TODO implement proper game type
class Projectile(
        val game: Game,
        val key: Int,
        val richtungsvektor: Vektor,
        sprite: BufferedImage,
        var lifetime: Int,
        val pos: MutableVektor,
        var pierce: Int = 1,
        val size: Double = 16.0,
        val damage: Double = 1.0
) : Actor(false, sprite) {
    var enemiesHit: List<Enemy> = emptyList()  // TODO implement enemy interface

    init {
        if (lifetime < 0) throw IllegalArgumentException("Lifetime must not be below zero!")
        if (pierce < 0) throw IllegalArgumentException("Pierce must not be below zero!")
        if (size < 0) throw IllegalArgumentException("Size must not be below zero!")
        direction = richtungsvektor.getAngle()
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
            if (!isTouchingEnemy(enemy)) continue

            if (pierce <= 0) {
                despawn()
                return
            }

            onEnemyTouched(enemy)
        }
    }

    fun despawn() {
        gameGrid?.removeActor(this)
        game.activeProjectiles.remove(key)
    }

    fun isTouchingEnemy(enemy: Enemy): Boolean =
            (Vektor.dist(pos, enemy.pos, false) < (size + enemy.size).pow(2)) and (enemy !in enemiesHit)

    fun onEnemyTouched(enemy: Enemy) {  // TODO implement proper enemy type

    }

    override fun getDirection(): Double = richtungsvektor.getAngle()
}