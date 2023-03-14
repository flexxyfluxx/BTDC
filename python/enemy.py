# -*- coding=utf-8 -*-

from ch.aplu.jgamegrid import Actor
from de.wvsberlin import MutableVektor, Gerade


class Enemy(Actor):
    def __init__(self, game, key, dmg, health, speed, sprite):
        Actor.__init__(self, sprite)
        self.game = game
        self.key = key
        self.dmg = dmg
        self.health = health
        self.speed = speed
        self.pathNodes = self.game.gameMap.pathNodes
        self.currentSegmentIdx = 0

        prevNodeToNextNodeVektor = self.pathNodes[1] - self.pathNodes[0]
        self.currentSegmentLength = abs(prevNodeToNextNodeVektor)
        self.segmentRichtungsvektor = prevNodeToNextNodeVektor.getUnitized() * self.speed

        self.pos = MutableVektor.fromImmutable(self.pathNodes[0])
        self.currentSegmentProgress = 0

    def tick(self):
        pass

    def despawn(self):
        self.game.grid.removeActor(self)
        self.game.activeEnemies.pop(self.key)

    def nextSegment(self):
        self.currentSegmentIdx += 1

        prevNodeToNextNodeVektor = self.pathNodes[self.currentSegmentIdx+1] - self.pathNodes[self.currentSegmentIdx]
        self.currentSegmentLength = abs(prevNodeToNextNodeVektor)
        self.segmentRichtungsvektor = prevNodeToNextNodeVektor.getUnitized() * self.speed
        self.currentSegmentProgress = 0

        if self.currentSegmentIdx > len(self.pathNodes)-2:
            self.game.health -= self.dmg
            self.despawn()

    def move(self, factor=1):
        self.currentSegmentProgress += self.speed * factor
        self.pos += self.segmentRichtungsvektor * factor
