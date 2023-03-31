# -*- coding: utf-8 -*-
from __future__ import print_function
import syspaths
from de.wvsberlin import JMainFrame, HeldTower
from de.wvsberlin.vektor import Vektor
from game import Game, Difficulty
from maps import theMaps
from towers import Tower1, Tower2, Tower3
from javax.swing import ImageIcon
from os.path import abspath

class Menu(JMainFrame):
    def __init__(self):
        JMainFrame.__init__(self)
        self.game = None

        # listen mit objekten der jeweiligen screens um diese ein- und auszublenden
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
            self.lgooseCondition,
            self.bConfirm,
            self.lgameOver
        ]

        self.winScreen = [
            self.bConfirm,
            self.lwon
        ]

        self.settingsScreen = [
            self.bDebug,
            self.bBack
        ]

        # läd das Bild der Goose condition
        # in python, da es in Java keinen Weg gab den relativen path zu verwenden
        # du goose condition kommt daher das Florian sich beim schreiben der Task für die loose conditon vertippt hatte
        self.lgooseCondition.setIcon(ImageIcon(abspath('../assets/sprites/gooseCondition.png')))

    # ein- und ausblenden der einzelnen Screens
    def toggleMainMenu(self, toggle):
        for e in self.mainMenu:
            e.setVisible(toggle)

    def toggleMapSelector(self, toggle):
        for e in self.mapSelector:
            e.setVisible(toggle)

    def toggleGameScreen(self, toggle):
        for e in self.gameScreen:
            e.setVisible(toggle)
        # da es keinen 4. Tower gibt ist der Button nur verfügbar, wenn er durch debug eine Funktion bekommt
        self.bTower4.setEnabled(self.bDebug.isSelected())

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

    def toggleSettingsScreen(self, toggle):
        for e in self.settingsScreen:
            e.setVisible(toggle)
        if toggle:
            self.bBack.setBounds(560, 300, 80, 24)
        elif not toggle:
            self.bBack.setBounds(895, 420, 80, 24)

    def setCurrentScreen(self, screen):
        self.toggleMainMenu(False)
        self.toggleMapSelector(False)
        self.toggleGameScreen(False)
        self.toggleConfirmScreen(False)
        self.toggleGameOverScreen(False)
        self.toggleWinScreen(False)
        self.toggleSettingsScreen(False)
        if screen == 0:
            self.toggleMainMenu(True)
        elif screen == 1:
            self.toggleMapSelector(True)
        elif screen == 2:
            self.toggleGameScreen(True)
        elif screen == 3:
            self.toggleConfirmScreen(True)
        elif screen == 4:
            self.toggleGameOverScreen(True)
        elif screen ==5:
            self.toggleWinScreen(True)
        elif screen == 6:
            self.toggleSettingsScreen(True)
        else:
            raise ValueError("illegal screen id")

    # öffnen des Map Selectors
    def bSelectMap_ActionPerformed(self, _):
        self.setCurrentScreen(1)

    # Verlassen des Map Selectors
    def bBack_ActionPerformed(self, _):
        self.setCurrentScreen(0)

    # Verlassen des Spiels
    def bQuitGame_ActionPerformed(self, _):
        self.dispose()

    # öffnen des screens zum Verlassen der Map
    def bQuit_ActionPerformed(self, _):
        self.gamegrid.doPause()
        self.setCurrentScreen(3)

    # Abbrechen des Verlassens der Map
    def bAbort_ActionPerformed(self, _):
        self.gamegrid.doRun()
        self.setCurrentScreen(2)

    # Verlassen der map
    def bConfirm_ActionPerformed(self, _):
        self.game.removeAllActors()
        self.game = None
        self.setCurrentScreen(0)

    def startGame(self):
        if self.bDebug.isSelected():
            print("startGame called")
            print("map =", self.getSelectedMap())
            print("difficulty =", self.getSelectedDifficulty())

        try:
            self.game = Game(self, self.getSelectedDifficulty(), theMaps[self.getSelectedMap()])
        except IndexError:
            self.setCurrentScreen(1)
            if self.bDebug.isSelected():
                print("Illegal map ID; returning to map select.")
            return
        self.game.grid.doRun()

    def bStartGame_ActionPerformed(self, _):
        if self.bDebug.isSelected():
            print("bStartGame_ActionPerformed called")

        self.setCurrentScreen(2)
        self.startGame()

    # startet die nächste Runde
    def bStartRound_ActionPerformed(self, _):
        self.game.startNextRound()

    # Auswählen der Tower zum Platzieren
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
        # wenn debug in den Einstellungen angeschaltet wurde kann man durch den Tower 3 button
        # jede Stelle anzeigen lassen, an der man Tower platzieren kann
        if self.game.debug:
            for x in range(0, 96):
                for y in range(0, 54):
                    self.game.heldTower = HeldTower(3) 
                    self.game.placeHeldTower(Vektor(x*10, y*10))
        else:
            if (self.game.money >= Tower3.cost) and (self.game.heldTower is None):
                self.game.heldTower = newHeldTower = HeldTower(2)
                self.gamegrid.addActor(newHeldTower, newHeldTower.pos.toLocation())
                self.game.updateMoney(-Tower3.cost)

    def bTower4_ActionPerformed(self, _):
        # wenn debug in den Einstellungen angeschaltet wurde kann man durch den Tower 4 button
        # Runden überspringen
        if self.game.debug:
            self.game.currentRound += 1
            self.tCurrentRound.setText(str(self.game.currentRound+1))
        else:
            raise NotImplementedError("This tower has not been implemented so far.")
        
    # Upgraden des ausgewählten Towers
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

    # Verkaufen des ausgewählten Towers
    def bSell_ActionPerformed(self, _):
        self.game.sellTower()

    # Abwählen des ausgewählten Towers 
    # sowohl zum Platzieren ausgwählte als auch normal ausgewählte Tower werden abgewählt
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

    # öffnet die Einstellungen, die den Debug Toggle enthalten
    def bSettings_ActionPerformed(self, _):
        self.setCurrentScreen(6)

if __name__ == "__main__":
    menu = Menu()
    menu.setVisible(True)
