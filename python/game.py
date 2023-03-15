# -*- coding: utf-8 -*-

import maputil as mu
from rounds import getAllRounds, getRound
from ch.aplu.jgamegrid import Location, GGMouse
from Tower1 import *
from Tower2 import *
from de.wvsberlin import Difficulty


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = 0
        self.roundActive = False
        self.gameMap = gameMap
        self.grid.mouseReleased = self.mouseReleased
        self.heldTower = None
        self.selectedTower = None
        self.towers = []

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

    def selectTower(self, actor, mouse, location):
        self.selectedTower = actor

    def mouseReleased(self, event):
        if self.heldTower != None:
            self.grid.removeActor(self.heldTower)
            if self.heldTower.towerID == 0:
                tower = Tower1(event.getX(), event.getY())
            elif self.heldTower.towerID == 1:
                tower = Tower2(event.getX(), event.getY())
            self.towers.append(tower)
            self.grid.addActor(tower, Location(event.getX(), event.getY()))
            tower.addMouseTouchListener(self.selectTower, GGMouse.lPress)
            del self.heldTower
            self.heldTower = None
        elif self.selectedTower != None:
            self.selectedTower.targetX = event.getX()
            self.selectedTower.targetY = event.getY()
            self.selectedTower = None


if __name__ == "__main__":
    pass
