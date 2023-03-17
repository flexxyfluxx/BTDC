from TowerBaseClass import Tower
from os.path import abspath


class Tower2(Tower):
    def __init__(self, pos, key):
        Tower.__init__(self, atkSpeed=400, atkDmg=400, atkRange=300, cost=300, pos=pos,
                       atkSpeedIncrement=1.01, atkDmgIncrement=1.3,
                       sprite=abspath('../assets/sprites/sprite2.png'), key=key)

    def sonderenemyzerstoerer(self):
        pass

    def getClosestEnemy(self):
        raise NotImplementedError("This function has not been implemented yet!")
