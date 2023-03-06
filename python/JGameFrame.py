# -*- coding: utf-8 -*-
import syspaths
from de.wvsberlin import JMainFrame


class JGameFrame(JMainFrame):
    def __init__(self, **kwargs):
        JMainFrame.__init__(self, **kwargs)
        self.mainMenu = [self.bSelectMap, 
                         self.bUpgrades,
                         self.bSettings,
                         self.bQuitGame]
        self.mapSelector = [self.jButtonGroupMaps,
                            self.jButtonGroupDifficulty,
                            self.bStartGame,
                            self.bBack]
        self.gameScreen = [self.jGridPanel,
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
                           self.bAutostart]
        self.confirmScreen = [self.bConfirm,
                              self.bAbort]
        

    def toggleMainMenu(self, toggle):
        for e in self.mainMenu:
            e.setVisible(toggle)

    def toggleMapSelector(self, toggle):
        for e in self.mapSelector:
            e.setVisible(toggle)

    def toggleGameScreen(self, toggle):
        for e in self.gameScreen:
            e.setVisible(toggle)

    def toggleConfirmScreen(self, toggle):
        for e in self.confirmScreen:
            e.setVisible(toggle)

    def toggleScreen(self, screen):
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
        
    def bSelectMap_ActionPerformed(self, event):
        self.toggleScreen(1)

    def bBack_ActionPerformed(self, event):
        self.toggleScreen(0)

    def bQuitGame_ActionPerformed(self, event):
        self.dispose()

    def bQuit_ActionPerformed(self, event):
        self.gamegrid.doPause()
        self.toggleScreen(3)
    
    def bAbort_ActionPerformed(self, event):
        self.gamegrid.doRun()
        self.toggleScreen(2)
    
    def bConfirm_ActionPerformed(self, event):
        self.toggleScreen(0)
        # implement stop game


if __name__ == "__main__":
    frame = JGameFrame()
    frame.toggleScreen(3)
    frame.setVisible(True)
