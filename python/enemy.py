# -*- coding=utf-8 -*-

from ch.aplu.jgamegrid import Actor
from maps import theMaps


class Enemy(Actor):
    def __init__(self, game, key, dmg, health, speed):
        Actor.__init__(self, "sprite")
        self.game = game
        self.key = key
        self.dmg = dmg
        self.health = health
        self.speed = speed
        self.theMap = theMaps[0]
        
    def despawn(self):
        self.game.grid.removeActor(self)
        self.game.activeEnemies.pop(self.key)
