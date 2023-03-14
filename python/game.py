# -*- coding: utf-8 -*-

import maputil as mu
from rounds import getAllRounds, getRound
from de.wvsberlin import Difficulty


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = 0
        self.roundActive = False
        self.gameMap = gameMap

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

    def spawnEnemy(self, enemyType):
        pass


if __name__ == "__main__":
    pass
