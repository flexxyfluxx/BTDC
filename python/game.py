# -*- coding: utf-8 -*-

from __future__ import print_function
import syspaths
import maputil as mu
from rounds import ROUNDS
from ch.aplu.jgamegrid import Location, GGMouse, Actor
from tower1 import Tower1
from tower2 import Tower2
from de.wvsberlin import Difficulty
from de.wvsberlin.vektor import Vektor
from counter import Counter

DEBUG = True


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(20)
        self.currentRound = -1
        self.roundActive = False
        self.gameMap = gameMap
        self.gameMap.setBgOfGrid(self.grid, debug=DEBUG)
        self.grid.mousePressed = self.mousePressed
        self.heldTower = None
        self.selectedTower = None

        self.doAutostart = False
        self.paused = True

        self.rounds = ROUNDS(self)

        self.enemyKeyGen = Counter()
        self.activeEnemies = {}  # Ein Dict löst viele Probleme, was Löschen von toten Gegnern angeht

        self.projectileKeyGen = Counter()
        self.activeProjectiles = {}

        self.towerKeyGen = Counter()
        self.towers = {}

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

        self.tickActor = TickActor(self)
        self.grid.addActor(self.tickActor, Location())

    def startNextRound(self):
        if DEBUG:
            print("[INFO] Round started.")
        self.currentRound += 1
        self.paused = False

    def tick(self):
        if self.paused:  # don't take action if paused
            return

        try:
            self.rounds[self.currentRound].tick()
        except StopIteration:
            if DEBUG:
                print("[INFO] Round exhausted.")
            if not self.activeEnemies:
                if DEBUG:
                    print("[INFO] Round ended.")
                for projectile in self.activeProjectiles.values():
                    projectile.despawn()  # clear projectiles to start next round w/ clean slate
                if not self.doAutostart:
                    self.paused = True
                    return

                self.startNextRound()

        for tower in self.towers.values():
            tower.tick()

        for enemy in self.activeEnemies.values():
            enemy.tick()

        for projectile in self.activeProjectiles.values():
            projectile.tick()

    def spawnEnemy(self, enemySupplier, segmentIdx=0, segmentProgress=0):
        key = next(self.enemyKeyGen)
        newEnemy = enemySupplier(self, key, segmentIdx, segmentProgress)
        self.activeEnemies[key] = newEnemy
        self.grid.addActor(newEnemy, self.gameMap.pathNodes[0].toLocation())
        if DEBUG:
            print("[INFO] Enemy spawned at segment: %s, segment progress: %s" % (segmentIdx, segmentProgress))
        return newEnemy

    def spawnProjectile(self, pos, direction, projSupplier):
        if DEBUG:
            print("[INFO] Projectile spawned at")
        key = next(self.projectileKeyGen)
        newProjectile = projSupplier(self, direction, pos)
        self.activeProjectiles[key] = newProjectile
        self.grid.addActor(newProjectile, pos.toLocation())
        newProjectile.show()
        return newProjectile

    def selectTower(self, actor, mouse, location):
        self.selectedTower = actor

    def placeHeldTower(self, pos):
        if DEBUG:
            print("[INFO] placeHeldTower called.")
        if self.heldTower is None:
            if DEBUG:
                print("[INFO] heldTower is None, aborting.")
            return

        # implement check for illegal positions
        if DEBUG:
            print("Start finding distance to path...")
        dist = 2048  # unreasonably large distance to start off with
        for e in range(len(self.gameMap.pathNodes) - 1):
            node1 = self.gameMap.pathNodes[e]
            node2 = self.gameMap.pathNodes[e + 1]
            clampedDist = Vektor.clampedDist(node1, node2, pos)
            if DEBUG:
                print("clampedDist =", clampedDist)
            if clampedDist < dist:
                dist = clampedDist
        if DEBUG:
            print("final dist to path:", dist)
        if dist >= 40:
            self.grid.removeActor(self.heldTower)
            key = next(self.towerKeyGen)
            if self.heldTower.towerID == 0:
                tower = Tower1(pos, key)
            elif self.heldTower.towerID == 1:
                tower = Tower2(pos, key)
            else:
                raise ValueError("Illegal tower ID")

            self.towers[key] = tower
            self.grid.addActor(tower, pos.toLocation())
            tower.addMouseTouchListener(self.selectTower, GGMouse.lPress)
            self.heldTower = None

    def changeTowerTarget(self, pos):
        if DEBUG:
            print(self.selectedTower.targetPos.x, self.selectedTower.targetPos.y, sep=", ")

        self.selectedTower.targetPos = pos
        if DEBUG:
            print(self.selectedTower.targetPos.x, self.selectedTower.targetPos.y, sep=", ")

        self.selectedTower = None

    def mousePressed(self, event):
        clickPos = Vektor(event.getX(), event.getY())
        if DEBUG:
            print("[INFO] Mouse click at (%s, %s)" % (clickPos.x, clickPos.y))
        if self.heldTower is not None:
            self.placeHeldTower(clickPos)
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
        self.grid.removeActor(self.tickActor)
        self.grid.getBg().clear()


class TickActor(Actor):
    def __init__(self, game):
        self.game = game

    def act(self):
        self.game.tick()


if __name__ == "__main__":
    pass
