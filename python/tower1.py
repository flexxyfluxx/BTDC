from projectiles import TOWER1_PROJ
from tower import Tower
from os.path import abspath


class Tower1(Tower):
    cost = 250

    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=5, attackDamage=6, attackRange=40, pos=pos,
                       attackSpeedIncrement=1.05, attackDamageIncrement=1.15,
                       costUpgradeAttackSpeed=400, costUpgradeAttackDamage=275,
                       costUpgrade3=420, projectile=TOWER1_PROJ,
                       sprite=abspath('../assets/sprites/sprite.png'), key=key, game=game)

    def upgradePath3(self):
        if self.costUpgrade3 <= self.game.money:
            self.game.updateMoney(-self.costUpgrade3)
            self.attackRange += 5
            self.costUpgrade3 *= 1.1
            print(self.attackRange)
