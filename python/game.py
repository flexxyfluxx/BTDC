# -*- coding: utf-8 -*-

from de.wvsberlin import Difficulty


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = 0
        self.roundActive = False
        self.gameMap = gameMap

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
        pass

    def spawnEnemy(self, enemySupplier, segmentIdx=0, segmentProgress=0):
        key = next(self.enemyKeyGen)
        newEnemy = enemySupplier(self, key, segmentIdx, segmentProgress)
        self.activeEnemies[key] = newEnemy
        self.grid.addActor(newEnemy, self.gameMap.nodes[0].toLocation())
        return newEnemy

    def spawnProjectile(self, location, direction, projSupplier):
        key = next(self.projectileKeyGen)
        newProjectile = projSupplier(self, direction, location)
        self.activeProjectiles[key] = newProjectile
        self.grid.addActor(newProjectile)
        return newProjectile


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
