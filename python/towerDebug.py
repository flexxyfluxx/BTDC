# -*- coding=utf-8 -*-
from tower import Tower
from os.path import abspath
from projectiles import EXAMPLE_PROJ


class TowerDebug(Tower):
    cost = 0
    def __init__(self, pos, key, game):
        Tower.__init__(self, attackSpeed=1, attackDamage=1, attackRange=1, pierce=1, pos=pos,
                       attackSpeedIncrement=1, attackDamageIncrement=1, 
                       costUpgradeAttackSpeed=0, costUpgradeAttackDamage=0, 
                       costUpgrade3=0, upgrade3Text="None", projectile=EXAMPLE_PROJ,
                       sprite=abspath('../assets/sprites/spriteDebug.png'), key=key, game=game)
        
    def tick(self): # leere tick funktion, damit der tower nicht attacken kann und das spiel crash
        pass