# -*- coding=utf-8 -*-
import syspaths
from ch.aplu.jgamegrid import Actor
from os.path import abspath


class Tower(Actor):
    def __init__(self, attackSpeed, attackDamage, attackRange, pierce, pos, attackSpeedIncrement, attackDamageIncrement,
                 costUpgradeAttackSpeed, costUpgradeAttackDamage, costUpgrade3, upgrade3Text, projectile, sprite, key, game):
        Actor.__init__(self, True, abspath(sprite))
        self.attackSpeed = attackSpeed
        self.attackDamage = attackDamage
        self.pierce = pierce
        self.pos = pos
        self.attackSpeedIncrement = attackSpeedIncrement
        self.attackDamageIncrement = attackDamageIncrement
        self.attackCooldown = 100/attackSpeed
        self.attackRange = attackRange
        self.targetDirection = 0
        self.costUpgradeAttackSpeed = costUpgradeAttackSpeed
        self.costUpgradeAttackDamage = costUpgradeAttackDamage
        self.costUpgrade3 = costUpgrade3
        self.projectile = projectile
        self.key = key
        self.game = game
        self.upgrade3Text = upgrade3Text

    def upgradeAttackSpeed(self):
        # Attack speed increment as a factor, eg. every level, attack speed is multiplied by 1.15
        if self.costUpgradeAttackSpeed > self.game.money:
            return  # not enough money => cancel
        
        self.game.updateMoney(-self.costUpgradeAttackSpeed)
        self.attackSpeed = self.attackSpeed * self.attackSpeedIncrement
        self.costUpgradeAttackSpeed *= 1.1
        self.game.updateCost()

    def upgradeAttackDamage(self):
        # Attack damage increment as a factor, eg. every level, attack damage is multiplied by 1.15
        if self.costUpgradeAttackDamage > self.game.money:
            return
        
        self.game.updateMoney(-self.costUpgradeAttackDamage)
        self.attackDamage = self.attackDamage * self.attackDamageIncrement
        self.costUpgradeAttackDamage *= 1.1
        self.game.updateCost()

    def attack(self):
        self.game.spawnProjectile(self.pos, self.targetDirection, self.projectile, self.attackRange, self.pierce)

    def tick(self): # eigene tick funktion, damit auch außerhalb der runden noch mit dem gamegrid interagiert werden kann
        self.attackCooldown -= 1
        if self.attackCooldown > 0:  # no attack ready yet
            return
        
        self.attack()
        self.attackCooldown = 100 / self.attackSpeed
