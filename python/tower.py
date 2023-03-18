import syspaths
from ch.aplu.jgamegrid import Actor
from os.path import abspath
from de.wvsberlin.vektor import Vektor


class Tower(Actor):
    def __init__(self, attackSpeed, attackDamage, attackRange, pos, attackSpeedIncrement, attackDamageIncrement, costUpgradeAttackSpeed, costUpgradeAttackDamage, costUpgrade3, projectile, sprite, key, game):
        Actor.__init__(self, abspath(sprite))
        self.attackSpeed = attackSpeed
        self.attackDamage = attackDamage
        self.pos = pos
        self.attackSpeedIncrement = attackSpeedIncrement
        self.attackDamageIncrement = attackDamageIncrement
        self.attackCooldown = 100/attackSpeed
        self.attackRange = attackRange
        self.targetPos = Vektor(480, 270)
        self.costUpgradeAttackSpeed = costUpgradeAttackSpeed
        self.costUpgradeAttackDamage = costUpgradeAttackDamage
        self.costUpgrade3 = costUpgrade3
        self.projectile = projectile
        self.key = key
        self.game = game

    def upgradeAttackSpeed(self):
        # Attack speed increment as a factor, eg. every level, attack speed is multiplied by 1.15
        if self.costUpgradeAttackSpeed >= self.game.money:
            self.game.updateMoney(-self.costUpgradeAttackSpeed)
            self.attackSpeed = self.attackSpeed * self.attackSpeedIncrement
            self.costUpgradeAttackSpeed *= 1.1
            print(self.attackSpeed)

    def upgradeAttackDamage(self):
        if self.costUpgradeAttackDamage >= self.game.money:
            self.game.updateMoney(-self.costUpgradeAttackDamage)
            self.attackDamage = self.attackDamage * self.attackDamageIncrement
            self.costUpgradeAttackDamage *= 1.1
            print(self.attackDamage)

    def attack(self):
        direction = (self.targetPos - self.pos).getAngle()
        self.game.spawnProjectile(self.pos, direction, self.projectile, self.attackRange)

    def tick(self):
        self.attackCooldown -= 1
        if self.attackCooldown <= 0:
            self.attack()
            self.attackCooldown = 100 / self.attackSpeed
