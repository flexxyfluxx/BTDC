# -*- coding: utf-8 -*-

from __future__ import print_function
import syspaths
import maputil as mu
from rounds import ROUNDS
from ch.aplu.jgamegrid import Location, GGMouse, Actor
from towers import Tower1, Tower2, Tower3
from towerDebug import TowerDebug
from de.wvsberlin import Difficulty
from de.wvsberlin.vektor import Vektor
from counter import Counter

class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gamegrid
        self.grid.setSimulationPeriod(20)
        self.currentRound = -1
        self.roundActive = False
        self.gameMap = gameMap
        self.debug = self.menu.bDebug.isSelected()
        self.gameMap.setBgOfGrid(self.grid, debug=self.debug)
        self.grid.mousePressed = self.mousePressed
        self.heldTower = None
        self.selectedTower = None

        self.paused = True

        self.rounds = ROUNDS(self)

        self.enemyKeyGen = Counter()
        self.activeEnemies = {}  # Ein Dict löst viele Probleme, was Löschen von toten Gegnern angeht

        self.projectileKeyGen = Counter()
        self.activeProjectiles = {} # Ein Dict löst auch viele Probleme, was Löschen Projektilen angeht

        self.towerKeyGen = Counter()
        self.towers = {} # guess what
        
        # Anzeigen werden geladen
        self.menu.lTower1.setText(str(Tower1.cost))
        self.menu.lTower2.setText(str(Tower2.cost))
        if self.debug:
            self.menu.lTower3.setText(str(TowerDebug.cost))
        else:
            self.menu.lTower3.setText(str(Tower3.cost))
        self.menu.lTower4.setText(str(0))

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

        self.menu.tMoney.setText(str(self.money))
        self.menu.tHealth.setText(str(self.health))
        self.menu.tCurrentRound.setText(str(self.currentRound + 1))

        self.tickActor = TickActor(self)
        self.grid.addActor(self.tickActor, Location())

    def startNextRound(self):
        if self.debug:
            print("[INFO] Round started.")
        if (self.currentRound+1) == len(self.rounds):
            self.win()
        self.currentRound += 1
        self.menu.tCurrentRound.setText(str(self.currentRound + 1))
        self.paused = False

    def tick(self): # eigene tick funktion, damit auch außerhalb der runden noch mit dem gamegrid interagiert werden kann
        if self.paused:  # don't take action if paused
            return

        try:
            self.rounds[self.currentRound].tick()
        except StopIteration:
            if self.debug:
                print("[INFO] Round exhausted.")
            if not self.activeEnemies:
                if self.debug:
                    print("[INFO] Round ended.")
                self.updateMoney(self.rounds[self.currentRound].reward)
                for projectile in self.activeProjectiles.values():
                    projectile.despawn()  # clear projectiles to start next round w/ clean slate
                if not self.menu.bAutostart.isSelected():
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
        if self.debug:
            print("[INFO] Enemy spawned at segment: %s, segment progress: %s" % (segmentIdx, segmentProgress))
        return newEnemy

    def spawnProjectile(self, pos, direction, projSupplier, lifetime, pierce):
        if self.debug:
            print("[INFO] Projectile spawned at")
        key = next(self.projectileKeyGen)
        newProjectile = projSupplier(self, key, direction, pos, lifetime, pierce)
        self.activeProjectiles[key] = newProjectile
        self.grid.addActor(newProjectile, pos.toLocation())
        newProjectile.show()
        return newProjectile

    def selectTower(self, actor, mouse, location):
        self.selectedTower = actor
        self.updateCost()

    def checkPlacementPos(self, pos): # überprüft ob der Tower, der plaziert werden soll weit genug vom Path entfernt ist
        dist = 2048  # unreasonably large distance to start off with
        for e in range(len(self.gameMap.pathNodes) - 1):
            node1 = self.gameMap.pathNodes[e]
            node2 = self.gameMap.pathNodes[e + 1]
            clampedDist = Vektor.clampedDist(node1, node2, pos)
            if self.debug:
                print("clampedDist =", clampedDist)
            if clampedDist < dist:
                dist = clampedDist
        if self.debug:
            print("final dist to path:", dist)
        return dist

    def placeHeldTower(self, pos):
        if self.debug:
            print("[INFO] placeHeldTower called.")
        if self.heldTower is None:
            if self.debug:
                print("[INFO] heldTower is None, aborting.")
            return

        # implement check for illegal positions
        if self.debug:
            print("Start finding distance to path...")
        dist = self.checkPlacementPos(pos)
        if dist >= 40:
            self.grid.removeActor(self.heldTower)
            key = next(self.towerKeyGen)
            if self.heldTower.towerID == 0:
                tower = Tower1(pos, key, self)
            elif self.heldTower.towerID == 1:
                tower = Tower2(pos, key, self)
            elif self.heldTower.towerID == 2:
                tower = Tower3(pos, key, self)
            elif self.heldTower.towerID == 3:
                tower = TowerDebug(pos, key, self)
            else:
                raise ValueError("Illegal tower ID")

            self.towers[key] = tower
            self.grid.addActor(tower, pos.toLocation())
            tower.addMouseTouchListener(self.selectTower, GGMouse.lPress)
            self.heldTower = None
        else:
            self.heldTower.pos = pos
            self.heldTower.setLocation(pos.toLocation())
            self.heldTower.show(4)

    def changeTowerTarget(self, pos):
        if self.debug:
            previousDirection = self.selectedTower.targetDirection
        targetVektor = pos - self.selectedTower.pos
        if targetVektor == Vektor.NullVektor:
            if self.debug:
                print("[INFO] Could not change target direction of tower %s because something something NullVektor."
                      % self.selectedTower.key)

            self.selectedTower = None
            return

        self.selectedTower.targetDirection = targetVektor.getAngle()

        if self.debug:
            print("[INFO] Updated target direction of tower %s from %s to %s."
                  % (self.selectedTower.key, previousDirection, self.selectedTower.targetDirection))

        self.selectedTower = None
        self.updateCost()

    def mousePressed(self, event):
        if event.button == 1: # ändert das Ziel des ausgewählten Towers
            clickPos = Vektor(event.getX(), event.getY())
            if self.debug:
                print("[INFO] Mouse click at (%s, %s)" % (clickPos.x, clickPos.y))
            if self.heldTower is not None:
                self.placeHeldTower(clickPos)
            elif self.selectedTower is not None:
                self.changeTowerTarget(clickPos)
        elif event.button == 3: # setzt den Tower um, falls man genug Geld hat
            if self.selectedTower is not None:
                clickPos = Vektor(event.getX(), event.getY())
                if self.money >= (self.selectedTower.cost // 2):
                    dist = self.checkPlacementPos(clickPos)
                    if dist >= 40:
                        self.selectedTower.pos = clickPos
                        self.selectedTower.setLocation(clickPos.toLocation())
                        self.updateMoney(-(self.selectedTower.cost // 2))
                        self.selectedTower = None
                        self.updateCost()

    def sellTower(self):
        if self.selectedTower is not None:
            self.updateMoney(self.selectedTower.cost // 2)
            self.grid.removeActor(self.selectedTower)
            self.towers.pop(self.selectedTower.key)
            self.selectedTower = None
            self.updateCost()

    def removeAllActors(self):
        # deinitialize the game
        if self.heldTower is not None:
            self.grid.removeActor(self.heldTower)
            self.heldTower = None
        if self.selectedTower is not None:
            self.selectedTower = None
            self.updateCost()
        for tower in self.towers.values():
            self.grid.removeActor(tower)
        for enemy in self.activeEnemies.values():
            self.grid.removeActor(enemy)
        for projectile in self.activeProjectiles.values():
            self.grid.removeActor(projectile)
        self.grid.removeActor(self.tickActor)
        self.grid.getBg().clear()

    def updateMoney(self, difference): # zum updaten der Geldanzeige
        self.money += difference
        self.menu.tMoney.setText(str(self.money))

    def updateCost(self): # zum updaten der Kostenanzeige
        if self.selectedTower is None:
            self.menu.tUpgrade1.setText(str())
            self.menu.tUpgrade2.setText(str())
            self.menu.tUpgrade3.setText(str())
            self.menu.lUpgrade3.setText("Towerspecific:")
        else:
            self.menu.tUpgrade1.setText(str(self.selectedTower.costUpgradeAttackSpeed))
            self.menu.tUpgrade2.setText(str(self.selectedTower.costUpgradeAttackDamage))
            self.menu.tUpgrade3.setText(str(self.selectedTower.costUpgrade3))
            self.menu.lUpgrade3.setText(self.selectedTower.upgrade3Text) # zeigt an was das Towerspezifische upgrade upgradet

    def gameOver(self):
        self.grid.doPause()
        self.menu.setCurrentScreen(4)

    def win(self):
        self.grid.doPause()
        self.menu.setCurrentScreen(5)

class TickActor(Actor): # hilfsactor für die eigene tick funktion
    def __init__(self, game):
        self.game = game

    def act(self):
        self.game.tick()


if __name__ == "__main__":
    pass
