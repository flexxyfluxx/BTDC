# -*- coding: utf-8 -*-

from __future__ import print_function
import syspaths
import maputil as mu
from rounds import ROUNDS
from ch.aplu.jgamegrid import Location, GGMouse
from Tower1 import Tower1
from Tower2 import Tower2
from TowerDebug import TowerDebug
from de.wvsberlin import Difficulty
from de.wvsberlin.vektor import Vektor

DEBUG = True


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = -1
        self.roundActive = False
        self.gameMap = gameMap
        self.gameMap.setBgOfGrid(self.grid, debug=DEBUG)
        self.grid.mousePressed = self.mousePressed
        self.heldTower = None
        self.selectedTower = None
        self.towerKeyGen = Counter()
        self.towers = {}

        self.enemyKeyGen = Counter()
        self.activeEnemies = {}  # Ein Dict löst viele Probleme, was Löschen von toten Gegnern angeht

        self.projectileKeyGen = Counter()
        self.activeProjectiles = {}

        if difficulty == Difficulty.EASY:
            self.health = 100
            self.money = 1000
        elif difficulty == Difficulty.NORMAL:
            self.health = 50
            self.money = 500
        elif difficulty == Difficulty.HARD:
            self.health = 1
            self.money = 250
        else:
            raise ValueError("Illegal difficulty")

    def startNextRound(self):
        self.currentRound += 1

    def tick(self):
        raise NotImplementedError("This method hasn't been implemented yet!")

    def spawnEnemy(self, enemySupplier, segmentIdx=0, segmentProgress=0):
        key = next(self.enemyKeyGen)
        newEnemy = enemySupplier(self, key, segmentIdx, segmentProgress)
        self.activeEnemies[key] = newEnemy
        self.grid.addActor(newEnemy, self.gameMap.nodes[0].toLocation())
        return newEnemy

    def spawnProjectile(self, location, direction, projSupplier, lifetime):
        key = next(self.projectileKeyGen)
        newProjectile = projSupplier(self, direction, location, lifetime)
        self.activeProjectiles[key] = newProjectile
        self.grid.addActor(newProjectile, location.toLocation())
        return newProjectile

    def selectTower(self, actor, mouse, location):
        self.selectedTower = actor
        print("tower selected")

    def placeTower(self, pos):
        # implement check for illegal positions
        dist = 2048  # unreasonably large distance to start off with
        for e in range(len(self.gameMap.pathNodes) - 1):
            node1 = self.gameMap.pathNodes[e]
            node2 = self.gameMap.pathNodes[e + 1]
            clampedDist = Vektor.clampedDist(node1, node2, pos)
            if DEBUG:
                print("clampedDist = ", clampedDist)
            if clampedDist < dist:
                dist = clampedDist
        if DEBUG:
            print("dist: ", dist)
            print("---")
        if dist >= 40:
            self.grid.removeActor(self.heldTower)
            key = next(self.towerKeyGen)
            if self.heldTower.towerID == 0:
                tower = Tower1(pos, key, self)
            elif self.heldTower.towerID == 1:
                tower = Tower2(pos, key, self)
            elif self.heldTower.towerID == 2:
                tower = TowerDebug(pos, key, self)
            else:
                raise ValueError("Illegal tower ID")

            self.towers[key] = tower
            self.grid.addActor(tower, pos.toLocation())
            tower.addMouseTouchListener(self.selectTower, GGMouse.lPress)
            self.heldTower = None
        else:
            self.heldTower.show(3)

    def changeTowerTarget(self, pos):
        if DEBUG:
            print(self.selectedTower.targetPos.x, self.selectedTower.targetPos.y, sep=", ")

        self.selectedTower.targetPos = pos
        if DEBUG:
            print(self.selectedTower.targetPos.x, self.selectedTower.targetPos.y, sep=", ")

        self.selectedTower = None

    def mousePressed(self, event):
        clickPos = Vektor(event.getX(), event.getY())
        print("Mouse click at (%s, %s)" % (clickPos.x, clickPos.y))
        if self.heldTower is not None:
            self.placeTower(clickPos)
        elif self.selectedTower is not None:
            self.changeTowerTarget(clickPos)

    def removeAllActors(self):
        # deinitialize the game
        if self.heldTower is not None:
            self.grid.removeActor(self.heldTower)
            self.heldTower = None
        if self.selectedTower is not None:
            self.selectedTower = None
        for tower in self.towers.values():
            self.grid.removeActor(tower)
        for enemy in self.activeEnemies.values():
            self.grid.removeActor(enemy)
        for projectile in self.activeProjectiles.values():
            self.grid.removeActor(projectile)
        self.grid.getBg().clear()

class Counter:
    def __init__(self):
        self.c = -1

    def __iter__(self):
        return self

    def next(self):
        self.c += 1
        return self.c


if __name__ == "__main__":
    pass
