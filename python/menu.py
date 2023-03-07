# -*- coding: utf-8 -*-
import syspaths
from de.wvsberlin import JMainFrame


class Menu:
    def __init__(self, **kwargs):
        self.frame = JMainFrame(**kwargs)

        self.mainMenu = [
            self.frame.bSelectMap,
            self.frame.bUpgrades,
            self.frame.bSettings,
            self.frame.bQuitGame
        ]
        self.mapSelector = [
            self.frame.jButtonGroupMaps,
            self.frame.jButtonGroupDifficulty,
            self.frame.bStartGame,
            self.frame.bBack
        ]
        self.gameScreen = [
            self.frame.jGridPanel,
            self.frame.bQuit,
            self.frame.bTower1,
            self.frame.bTower2,
            self.frame.bTower3,
            self.frame.bTower4,
            self.frame.bUpgrade1,
            self.frame.bUpgrade2,
            self.frame.bUpgrade3,
            self.frame.tRound,
            self.frame.jSeparator1,
            self.frame.bStartRound,
            self.frame.bAutostart
        ]
        self.confirmScreen = [
            self.frame.bConfirm,
            self.frame.bAbort
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
        self.frame.dispose()

    def bQuit_ActionPerformed(self, _):
        self.frame.gamegrid.doPause()   # TODO
        self.setCurrentScreen(3)

    def bAbort_ActionPerformed(self, _):
        self.frame.gamegrid.doRun()     # TODO
        self.setCurrentScreen(2)

    def bConfirm_ActionPerformed(self, _):
        self.setCurrentScreen(0)
        # implement stop game

    def setVisible(self, yesno):
        self.frame.setVisible(yesno)


if __name__ == "__main__":
    menu = Menu()
    menu.setCurrentScreen(3)
    menu.setVisible(True)
