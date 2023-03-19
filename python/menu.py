# -*- coding: utf-8 -*-
from __future__ import print_function
import syspaths
from de.wvsberlin import JMainFrame
from de.wvsberlin.vektor import Vektor
from game import Game, Difficulty
from maps import theMaps
from heldTower import HeldTower
from ch.aplu.jgamegrid import Location
from tower1 import Tower1
from tower2 import Tower2
from javax.swing import ImageIcon
from os.path import abspath

DEBUG = True


class Menu(JMainFrame):
    def __init__(self):
        JMainFrame.__init__(self)
        self.game = None

        self.mainMenu = [
            self.bSelectMap,
            self.bUpgrades,
            self.bSettings,
            self.bQuitGame
        ]

        self.mapSelector = [
            self.jButtonGroupMaps,
            self.jButtonGroupDifficulty,
            self.bStartGame,
            self.bBack
        ]

        self.gameScreen = [
            self.jGridPanel,
            self.bQuit,
            self.bTower1,
            self.bTower2,
            self.bTower3,
            self.bTower4,
            self.lTower1,
            self.lTower2,
            self.lTower3,
            self.lTower4,
            self.bUpgrade1,
            self.bUpgrade2,
            self.bUpgrade3,
            self.bSell,
            self.bDeselect,
            self.jSeparator1,
            self.bStartRound,
            self.bAutostart,
            self.tCurrentRound,
            self.tMoney,
            self.tHealth,
            self.lUpgrade1,
            self.lUpgrade2,
            self.lUpgrade3,
            self.tUpgrade1,
            self.tUpgrade2,
            self.tUpgrade3,
            self.lCurrentRound,
            self.lMoney,
            self.lHealth
        ]
        self.confirmScreen = [
            self.bConfirm,
            self.bAbort
        ]

        self.gameOverScreen = [
            self.gooseCondition,
            self.bConfirm,
            self.gameOver
        ]

        self.winScreen = [
            self.bConfirm,
            self.won
        ]

        self.gooseCondition.setIcon(ImageIcon(abspath('../assets/sprites/gooseCondition.png')))

    def toggleMainMenu(self, yesno):
        for e in self.mainMenu:
            e.setVisible(yesno)

    def toggleMapSelector(self, toggle):
        for e in self.mapSelector:
            e.setVisible(toggle)

    def toggleGameScreen(self, toggle):
        for e in self.gameScreen:
            e.setVisible(toggle)

    def toggleConfirmScreen(self, toggle):
        for e in self.confirmScreen:
            e.setVisible(toggle)

    def toggleGameOverScreen(self, toggle):
        for e in self.gameOverScreen:
            e.setVisible(toggle)
        if toggle:
            self.bConfirm.setBounds(620, 200, 80, 24)
        elif not toggle:
            self.bConfirm.setBounds(320, 330, 80, 24)

    def toggleWinScreen(self, toggle):
        for e in self.winScreen:
            e.setVisible(toggle)
        if toggle:
            self.bConfirm.setBounds(590, 180, 80, 24)
        elif not toggle:
            self.bConfirm.setBounds(320, 330, 80, 24)

    def setCurrentScreen(self, screen):
        if screen == 0:
            self.toggleMainMenu(True)
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleGameOverScreen(False)
            self.toggleWinScreen(False)
        elif screen == 1:
            self.toggleMainMenu(False)
            self.toggleMapSelector(True)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleGameOverScreen(False)
            self.toggleWinScreen(False)
        elif screen == 2:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleGameScreen(True)
            self.toggleConfirmScreen(False)
            self.toggleGameOverScreen(False)
            self.toggleWinScreen(False)
        elif screen == 3:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleGameOverScreen(False)
            self.toggleWinScreen(False)
            self.toggleConfirmScreen(True) #toggleWinScreen und toggleGameOverScreen müssen vertauscht werden, damit der confrim button sichtbar bleibt
        elif screen == 4:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleWinScreen(False) #toggleWinScreen und toggleGameOverScreen müssen vertauscht werden, damit der confrim button sichtbar bleibt
            self.toggleGameOverScreen(True)
        elif screen ==5:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleGameOverScreen(False)
            self.toggleWinScreen(True)
        else:
            raise ValueError("illegal screen id")

    def bSelectMap_ActionPerformed(self, _):
        self.setCurrentScreen(1)

    def bBack_ActionPerformed(self, _):
        self.setCurrentScreen(0)

    def bQuitGame_ActionPerformed(self, _):
        self.dispose()

    def bQuit_ActionPerformed(self, _):
        self.gamegrid.doPause()   # TODO
        self.setCurrentScreen(3)

    def bAbort_ActionPerformed(self, _):
        self.gamegrid.doRun()     # TODO
        self.setCurrentScreen(2)

    def bConfirm_ActionPerformed(self, _):
        self.game.removeAllActors()
        self.game = None
        self.setCurrentScreen(0)
        # implement stop game

    def startGame(self):
        if DEBUG:
            print("startGame called")
            print("map =", self.getSelectedMap())
            print("difficulty =", self.getSelectedDifficulty())

        try:
            self.game = Game(self, self.getSelectedDifficulty(), theMaps[self.getSelectedMap()])
        except IndexError:
            self.setCurrentScreen(1)
            if DEBUG:
                print("Illegal map ID; returning to map select.")
            return
        self.game.grid.doRun()

    def bStartRound_ActionPerformed(self, _):
        self.game.startNextRound()

    def bStartGame_ActionPerformed(self, _):
        if DEBUG:
            print("bStartGame_ActionPerformed called")

        self.setCurrentScreen(2)
        self.startGame()

    def bTower1_ActionPerformed(self, _):
        if (self.game.money >= Tower1.cost) and (self.game.heldTower is None):
            self.game.heldTower = newHeldTower = HeldTower(0)
            self.gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
            self.game.updateMoney(-Tower1.cost)

    def bTower2_ActionPerformed(self, _):
        if (self.game.money >= Tower2.cost) and (self.game.heldTower is None):
            self.game.heldTower = newHeldTower = HeldTower(1)
            self.gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
            self.game.updateMoney(-Tower2.cost)

    def bTower3_ActionPerformed(self, _):
        # raise NotImplementedError("This tower has not been implemented so far.")
        for x in range(0, 96):
            for y in range(0, 54):
                self.game.heldTower = HeldTower(2) 
                self.game.placeHeldTower(Vektor(x*10, y*10))

    def bTower4_ActionPerformed(self, _):
        # self.game.heldTower = 3

        self.game.currentRound += 1
        return
        raise NotImplementedError("This tower has not been implemented so far.")

    def bUpgrade1_ActionPerformed(self, _):
        if self.game.selectedTower is None:
            return
        self.game.selectedTower.upgradeAttackSpeed()

    def bUpgrade2_ActionPerformed(self, _):
        if self.game.selectedTower is None:
            return
        self.game.selectedTower.upgradeAttackDamage()

    def bUpgrade3_ActionPerformed(self, _):
        if self.game.selectedTower is None:
            return
        self.game.selectedTower.upgradePath3()

    def bSell_ActionPerformed(self, _):
        self.game.sellTower()

    def bDeselect_ActionPerformed(self, _):
        if self.game.selectedTower is not None:
            self.game.selectedTower = None
            self.game.updateCost()
        if self.game.heldTower is not None:
            if self.game.heldTower.towerID == 0:
                self.game.updateMoney(Tower1.cost)
            elif self.game.heldTower.towerID == 1:
                self.game.updateMoney(Tower2.cost)
            else:
                raise ValueError("Illegal tower ID")
            self.game.grid.removeActor(self.game.heldTower)
            self.game.heldTower = None


if __name__ == "__main__":
    menu = Menu()
    menu.setVisible(True)
