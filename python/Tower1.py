from TowerBaseClass import *


class Tower1(Tower):
    def __init__(self, pos, key):
        Tower.__init__(self, atkSpeed=15, atkDmg=5, atkRange=200, cost=250, pos=pos,
                       atkSpeedIncrement=1.05, atkDmgIncrement=1.15,
                       sprite=abspath('../assets/sprites/sprite.png'), key=key)

    def upgradeAtkRange(self):
        self.atkRange += 20
