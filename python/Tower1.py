from TowerBaseClass import Tower
from os.path import abspath
from projectiles import TOWER1_PROJ


class Tower1(Tower):
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=5, attackDamage=6, attackRange=40, cost=250, pos=pos,
                       attackSpeedIncrement=1.05, attackDamageIncrement=1.15, projectile=TOWER1_PROJ,
                       sprite=abspath('../assets/sprites/sprite.png'), key=key, game=game)

    def upgradePath3(self):
        self.attackRange += 5
        print(self.attackRange)