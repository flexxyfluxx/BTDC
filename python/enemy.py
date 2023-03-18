# -*- coding=utf-8 -*-

from __future__ import print_function
from ch.aplu.jgamegrid import Actor
from de.wvsberlin.vektor import Vektor, MutableVektor, Gerade


DEBUG = True


class Enemy(Actor):
    def __init__(self, game, key, dmg, health, speed, sprite, segmentIdx=0, segmentProgress=0,
                 childSupplier=None, childCount=1, childSpacing=10, sizeRadius=16):
        Actor.__init__(self, sprite)
        self.game = game
        self.key = key
        self.dmg = dmg
        self.health = health
        self.speed = speed
        self.pathNodes = self.game.gameMap.pathNodes
        self.currentSegmentIdx = segmentIdx
        self.size = sizeRadius

        prevNodeToNextNodeVektor = self.pathNodes[1] - self.pathNodes[0]
        self.currentSegmentLength = abs(prevNodeToNextNodeVektor)
        self.currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        self.currentSegmentRichtungsvektor = self.currentSegmentUnitVektor * self.speed

        self.currentSegmentProgress = segmentProgress

        self.pos = MutableVektor.fromImmutable(self.pathNodes[0]
                                               + self.currentSegmentUnitVektor * self.currentSegmentProgress)

        self.childSupplier = childSupplier
        self.childCount = childCount
        self.childSpacing = childSpacing

    def tick(self):
        if self.health <= 0:
            self.die(-self.health)

        self.currentSegmentProgress += self.speed

        if self.currentSegmentProgress >= self.currentSegmentLength:
            self.nextSegment()  # this will hopefully not fail to nuke everything if we exit the map...
            overshoot = self.currentSegmentProgress - self.currentSegmentLength
            self.pos = self.pathNodes[self.currentSegmentIdx] # + self.currentSegmentUnitVektor * overshoot
            self.setLocation(self.pos.toLocation())
            return

        self.pos += self.currentSegmentRichtungsvektor
        self.setLocation(self.pos.toLocation())

    def despawn(self):
        self.game.grid.removeActor(self)
        self.game.activeEnemies.pop(self.key)

    def die(self, overshoot):
        if DEBUG:
            print("Enemy", self.key, "died.")
        self.despawn()

        if self.childSupplier is None or overshoot <= 0:
            return

        child = self.game.spawnEnemy(self.childSupplier, self.currentSegmentIdx, self.currentSegmentProgress)
        if overshoot < child.health:
            child.health -= overshoot
            return

        child.die(overshoot - child.health)

    def nextSegment(self):
        self.currentSegmentIdx += 1

        if self.currentSegmentIdx > len(self.pathNodes)-2:
            self.game.health -= self.dmg
            self.despawn()
            return

        prevNodeToNextNodeVektor = self.pathNodes[self.currentSegmentIdx+1] - self.pathNodes[self.currentSegmentIdx]
        self.currentSegmentLength = abs(prevNodeToNextNodeVektor)
        self.currentSegmentUnitVektor = prevNodeToNextNodeVektor.getUnitized()
        self.currentSegmentRichtungsvektor = self.currentSegmentUnitVektor * self.speed
        self.currentSegmentProgress = 0

