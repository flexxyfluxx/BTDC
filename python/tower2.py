from tower import Tower
from os.path import abspath
from projectiles import TOWER2_PROJ


class Tower2(Tower):
    cost = 300
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=10, attackDamage=4, attackRange=20, pos=pos,
                       attackSpeedIncrement=1.01, attackDamageIncrement=1.3, projectile=TOWER2_PROJ,
                       sprite=abspath('../assets/sprites/sprite2.png'), key=key, game=game)
