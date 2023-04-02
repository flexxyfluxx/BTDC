package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import java.lang.IllegalArgumentException
import java.util.function.Supplier

class Tower1(pos: Vektor, key: Int, game: Game) : Tower(
        game = game,
        key = key,
        attackSpeed = 5,
        attackDamage = 2,
        attackRange = 40,
        pierce = 1,
        pos = pos,
        attackSpeedIncrement = 1.05,
        attackDamageIncrement = 1.15,
        attackSpeedUpgradeCost = 200,
        attackDamageUpgradeCost = 75,
        upgrade3Cost = 220,
        upgrade3Text = "Attack range",
        projectileSupplier = TODO(),
        sprite = Sprite.CR_TOWER
) {
    companion object {
        val cost: Int = 250
    }

    override fun upgradePath3() {
        if (upgrade3Cost > game.money) return

        game.updateMoney(-upgrade3Cost)
        attackRange += 5
        upgrade3Cost *= 1.1
    }
}

class Tower2(pos: Vektor, key: Int, game: Game) : Tower(
        game = game,
        key = key,
        attackSpeed = 10,
        attackDamage = 1,
        attackRange = 20,
        pierce = 1,
        pos = pos,
        attackSpeedIncrement = 1.01,
        attackDamageIncrement = 1.3,
        attackSpeedUpgradeCost = 150,
        attackDamageUpgradeCost = 320,
        upgrade3Cost = 200,
        upgrade3Text = "Piercing",
        projectileSupplier = TODO(),
        sprite = Sprite.EGIRL
) {
    companion object {
        val cost: Int = 300
    }

    override fun upgradePath3() {
        if (upgrade3Cost > game.money) return

        game.updateMoney(-upgrade3Cost)
        pierce++
        upgrade3Cost *= 1.2
        game.updateCost()
    }
}

class Tower3(pos: Vektor, key: Int, game: Game) : Tower(
        game = game,
        key = key,
        attackSpeed = 7,
        attackDamage = 3,
        attackRange = 25,
        pierce = 1,
        pos = pos,
        attackSpeedIncrement = 1.02,
        attackDamageIncrement = 1.4,
        attackSpeedUpgradeCost = 50,
        attackDamageUpgradeCost = 100,
        upgrade3Cost = 0,
        upgrade3Text = "No upgrade",
        projectileSupplier = TODO(),
        sprite = Sprite.PICASSO
) {
    companion object {
        val cost: Int = 0
    }

    override fun upgradePath3() {}
}

class TowerDebug(pos: Vektor, key: Int, game: Game) : Tower(
        game = game,
        key = key,
        attackSpeed = 1,
        attackDamage = 1,
        attackRange = 1,
        pierce = 1,
        pos = pos,
        attackSpeedIncrement = 1.0,
        attackDamageIncrement = 1.0,
        attackSpeedUpgradeCost = 0,
        attackDamageUpgradeCost = 0,
        upgrade3Cost = 0,
        upgrade3Text = "No upgrade",
        projectileSupplier = TODO(),
        sprite = Sprite.SQUARE_GREEN
) {
    companion object {
        val cost: Int = 0
    }

    override fun tick() {}

    override fun upgradePath3() {}
}

abstract class Tower(
        val game: Game,
        val key: Int,
        attackSpeed: Number,
        attackDamage: Number,
        attackRange: Int,
        pierce: Int,
        var pos: Vektor,
        val attackSpeedIncrement: Double,
        val attackDamageIncrement: Double,
        attackSpeedUpgradeCost: Number,
        attackDamageUpgradeCost: Number,
        upgrade3Cost: Number,
        val upgrade3Text: String,
        val projectileSupplier: ProjectileSupplier,
        sprite: BufferedImage
) : Actor(true, sprite) {
    var attackSpeed: Double
    var attackDamage: Double
    var attackInterval: Int
    var attackRange: Int
    var pierce: Int
    var targetDirection: Double = 0.0
    var attackSpeedUpgradeCost = attackSpeedUpgradeCost.toDouble()
    var attackDamageUpgradeCost = attackDamageUpgradeCost.toDouble()
    var upgrade3Cost = upgrade3Cost.toDouble()

    init {
        this.attackSpeed = attackSpeed.toDouble()
        if (this.attackSpeed <= 0) throw IllegalArgumentException("Attack speed must be greater than zero!")
        if (attackRange < 0) throw IllegalArgumentException("Attack range must not be negative!")
        if (pierce <= 0) throw IllegalArgumentException("Pierce must not be negative!")

        this.attackRange = attackRange
        this.attackDamage = attackDamage.toDouble()
        this.attackInterval = (100 / this.attackSpeed).toInt()
        this.pierce = pierce
    }

    fun upgradeAttackSpeed() {
        if (attackSpeedUpgradeCost > game.money) return

        game.updateMoney(-attackSpeedUpgradeCost)
    }

    fun upgradeAttackDamage() {
        if (attackDamageUpgradeCost > game.money) return

        game.updateMoney(-attackDamageUpgradeCost)
        attackDamage *= attackDamageIncrement
        attackDamageUpgradeCost *= 1.1
        game.updateCost()
    }

    abstract fun upgradePath3()

    open fun tick() {
    }

    open fun attack() = game.spawnProjectile(pos, targetDirection, projectileSupplier, attackRange, pierce)
}

class HeldTower(val towerID: Int) : Actor(sprites[towerID]) {
    val pos = Vektor(480, 270)

    init {
        show()
    }

    companion object {
        @JvmStatic
        // we should move away from towerIDs to figure out which sprite should be shown..
        val sprites: Array<BufferedImage> = arrayOf(
                Sprite.CR_TOWER, Sprite.EGIRL, Sprite.PICASSO, Sprite.SQUARE_GREEN, Sprite.DENIED
        )
    }
}
