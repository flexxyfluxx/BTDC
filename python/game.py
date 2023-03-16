# -*- coding: utf-8 -*-

import maputil as mu
from rounds import getAllRounds, getRound
from ch.aplu.jgamegrid import Location, GGMouse
from Tower1 import *
from Tower2 import *
from de.wvsberlin import Difficulty
from de.wvsberlin.vektor import Vektor


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = 0
        self.roundActive = False
        self.gameMap = gameMap
        self.gameMap.setBgOfGrid(self.grid)
        self.grid.mousePressed = self.mouseReleased
        self.heldTower = None
        self.selectedTower = None
        self.towerKeyGen = Counter()
        self.towers = {}
        self.enemiesKeyGen = Counter()
        self.enemies = {}
        self.menu.bUpgrade1_ActionPerformed = self.bUpgrade1_ActionPeformed
        self.menu.bUpgrade2_ActionPerformed = self.bUpgrade2_ActionPeformed
        self.menu.bUpgrade3_ActionPerformed = self.bUpgrade3_ActionPeformed

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

    def placeTower(self, event):
        #implement check for illegal positions
        dist = 2048
        for e in range(len(self.gameMap.pathNodes)-1):
            node1 = self.gameMap.pathNodes[e]
            node2 = self.gameMap.pathNodes[e+1]
            clampedDist = Vektor.clampedDist(node1, node2, Vektor(event.getX(), event.getY()))
            #print(clampedDist)
            if clampedDist < dist:
                dist = clampedDist
        #print("dist: " + str(dist))
        #print("---")
        if dist >= 80:
            self.grid.removeActor(self.heldTower)
            key = next(self.towerKeyGen)
            if self.heldTower.towerID == 0:
                tower = Tower1(event.getX(), event.getY(), key)
            elif self.heldTower.towerID == 1:
                tower = Tower2(event.getX(), event.getY(), key)
            self.towers[key] = tower
            self.grid.addActor(tower, Location(event.getX(), event.getY()))
            tower.addMouseTouchListener(self.selectTower, GGMouse.lPress)
            self.heldTower = None

    def changeTowerTarget(self, event):
        print(self.selectedTower.targetX, self.selectedTower.targetY)
        self.selectedTower.targetX = event.getX()
        self.selectedTower.targetY = event.getY()
        print(self.selectedTower.targetX, self.selectedTower.targetY)
        self.selectedTower = None
        
    def mouseReleased(self, event):
        if self.heldTower != None:
            self.placeTower(event)
        elif self.selectedTower != None:
            self.changeTowerTarget(event)

    def bUpgrade1_ActionPeformed(self, _):
        if self.selectedTower != None:
            print(self.selectedTower.asp)
            self.selectedTower.upgradeAttackSpeed()
            print(self.selectedTower.asp)
    
    def bUpgrade2_ActionPeformed(self, _):
        if self.selectedTower != None:
            print(self.selectedTower.admg)
            self.selectedTower.upgradeAttackDamage()
            print(self.selectedTower.admg)

    def bUpgrade3_ActionPeformed(self, _):
        if self.selectedTower != None:
            #self.heldTower.upgrade...()
            raise NotImplementedError("This upgrade has not been implemented so far.")

    def close(self):
        if self.heldTower != None:
            self.grid.removeActor(self.heldTower)
            self.heldTower = None
        if self.selectedTower != None:
            self.selectedTower = None
        for key in self.towers:
            self.grid.removeActor(self.towers[key])
            del self.towers[key]
        for key in self.enemies:
            self.grid.removeActor(self.towers[key])
            del self.enemies[key]
        #implement removing projectiles
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