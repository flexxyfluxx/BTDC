from TowerBaseClass import Tower
from os.path import abspath
from projectiles import EXAMPLE_PROJ


class TowerDebug(Tower):
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=10, attackDamage=4, attackRange=20, cost=300, pos=pos,
                       attackSpeedIncrement=1.01, attackDamageIncrement=1.3, projectile=EXAMPLE_PROJ,
                       sprite=abspath('../assets/sprites/spriteDebug.png'), key=key, game=game)
        
    def act(self):
        pass