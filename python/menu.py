# -*- coding: utf-8 -*-
from __future__ import print_function
import syspaths
from de.wvsberlin import JMainFrame
from game import Game, Difficulty
from round import Round, Wave
from maps import theMaps

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
            self.bUpgrade1,
            self.bUpgrade2,
            self.bUpgrade3,
            self.tRound,
            self.jSeparator1,
            self.bStartRound,
            self.bAutostart
        ]
        self.confirmScreen = [
            self.bConfirm,
            self.bAbort
        ]

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

    def setCurrentScreen(self, screen):
        if screen == 0:
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleMainMenu(True)
        elif screen == 1:
            self.toggleMainMenu(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(False)
            self.toggleMapSelector(True)
        elif screen == 2:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleConfirmScreen(False)
            self.toggleGameScreen(True)
        elif screen == 3:
            self.toggleMainMenu(False)
            self.toggleMapSelector(False)
            self.toggleGameScreen(False)
            self.toggleConfirmScreen(True)
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
        self.setCurrentScreen(0)
        # implement stop game

    def startGame(self):
        print("startGame called")
        print("map = ", self.getSelectedMap())
        print("difficulty = ", self.getSelectedDifficulty())
        self.game = Game(self, self.getSelectedDifficulty(), theMaps[self.getSelectedMap()])#, debug=DEBUG)
        self.game.grid.doRun()

    def bStartRound_ActionPerformed(self, _):
        self.game.startNextRound()

    def bStartGame_ActionPerformed(self, _):
        print("bStartGame_ActionPerformed called")
        self.setCurrentScreen(2)
        self.startGame()

    def bTower1_ActionPerformed(self, _):
        self.game.heldTower = 0

    def bTower2_ActionPerformed(self, _):
        self.game.heldTower = 1
    
    def bTower3_ActionPerformed(self, _):
        #self.game.heldTower = 2
        print("This tower does not currently exist.")

    def bTower4_ActionPerformed(self, _):
        #self.game.heldTower = 3
        print("This tower does not currently exist.")


if __name__ == "__main__":
    menu = Menu()
    menu.setVisible(True)
