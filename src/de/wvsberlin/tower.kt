package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage
import java.lang.IllegalArgumentException

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
        projectileSupplier = Projectile::TOWER1_PROJ,
        sprite = Sprite.CR_TOWER
) {
    override val cost: Int = Companion.cost

    companion object {
        const val cost = 250
    }

    override fun upgradePath3() {
        if (upgrade3Cost > game.money)
            return

        game.updateMoney(-upgrade3Cost)
        attackRange += 5
        upgrade3Cost = (upgrade3Cost * 1.1).toInt()  // god I hate this
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
        projectileSupplier = Projectile::TOWER2_PROJ,
        sprite = Sprite.EGIRL
) {
    override val cost = Companion.cost

    companion object {
        const val cost = 300
    }

    override fun upgradePath3() {
        if (upgrade3Cost > game.money)
            return

        game.updateMoney(-upgrade3Cost)
        pierce++
        upgrade3Cost = (upgrade3Cost * 1.2).toInt()
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
        projectileSupplier = Projectile::TOWER3_PROJ,
        sprite = Sprite.PICASSO
) {
    override val cost: Int = Companion.cost

    companion object {
        const val cost = 425
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
        projectileSupplier = Projectile::EXAMPLE_PROJ,
        sprite = Sprite.SQUARE_GREEN
) {
    override val cost: Int = Companion.cost

    companion object {
        const val cost = 0
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
        var attackSpeedUpgradeCost: Int,
        var attackDamageUpgradeCost: Int,
        var upgrade3Cost: Int,
        val upgrade3Text: String,
        val projectileSupplier: ProjectileSupplier,
        sprite: BufferedImage,
        val sizeRadius: Double = 16.0
) : Actor(true, sprite) {
    var attackSpeed: Double
    var attackDamage: Double
    var attackInterval: Int
    var attackCooldown: Int = 0
    var attackRange: Int
    var pierce: Int
    var targetDirection: Double = 0.0

    abstract val cost: Int

    init {
        this.attackSpeed = attackSpeed.toDouble()
        if (this.attackSpeed <= 0)
            throw IllegalArgumentException("Attack speed must be greater than zero!")
        if (attackRange < 0)
            throw IllegalArgumentException("Attack range must not be negative!")
        if (pierce <= 0)
            throw IllegalArgumentException("Pierce must not be negative!")

        this.attackRange = attackRange
        this.attackDamage = attackDamage.toDouble()
        this.attackInterval = (100 / this.attackSpeed).toInt()
        this.pierce = pierce
    }

    fun upgradeAttackSpeed() {
        if (attackSpeedUpgradeCost > game.money)
            return

        game.updateMoney(-attackSpeedUpgradeCost)
    }

    fun upgradeAttackDamage() {
        if (attackDamageUpgradeCost > game.money)
            return

        game.updateMoney(-attackDamageUpgradeCost)
        attackDamage *= attackDamageIncrement
        attackDamageUpgradeCost = (attackDamageUpgradeCost * 1.1).toInt()
        game.updateCost()
    }

    abstract fun upgradePath3()

    open fun tick() {
        if (attackCooldown > 0) {
            attackCooldown--
            return
        }
        attack()
        attackCooldown = attackInterval
    }

    open fun attack() = game.spawnProjectile(pos, targetDirection, projectileSupplier, attackRange, pierce, attackDamage)
}

class HeldTower(val towerID: Int) : Actor(sprites[towerID], Sprite.DENIED) {
    var pos = Vektor(480, 270)

    init {
        show(0)
    }

    fun getTower(pos: Vektor, key: Int, game: Game) =
            when (towerID) {
                0 -> Tower1(pos, key, game)
                1 -> Tower2(pos, key, game)
                2 -> Tower3(pos, key, game)
                3 -> TowerDebug(pos, key, game)
                else -> throw IllegalStateException("This heldTower has an illegal tower ID!")
                // throw exception here instead of during instantiation to make adding towers easier
                // ..also, Kotlin probably won't compile otherwise
            }

    companion object {
        @JvmStatic
        // we should move away from towerIDs to figure out which sprite should be shown..
        val sprites: Array<BufferedImage> = arrayOf(
                Sprite.CR_TOWER, Sprite.EGIRL, Sprite.PICASSO, Sprite.SQUARE_GREEN
        )
    }
}
