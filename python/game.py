# -*- coding: utf-8 -*-

from JGameFrame import JGameFrame
import maputil as mu
from enum import enum


class Menu:
    def __init__(self):
        self.gameFrame = JGameFrame(bStartRound_ActionPerformed=self.buttonStartNextRound,
                                    bStartGame_ActionPerformed=self.buttonStartGame)
        self.game = None  # es existiert noch kein Spiel => ist None

    def startGame(self):
        self.game = Game(self, self.gameFrame.getSelectedDifficulty())

    def buttonStartNextRound(self, event):
        self.game.startRound(self.difficulty, self.currentRound)

    def buttonStartGame(self, event):
        self.startGame()
        self.gameFrame.toggleGameScreen(2)


class Game:
    def __init__(self, menu, difficulty, gameMap):  # gameMap, bc map is taken by python and map_ is ugly
        self.menu = menu
        self.grid = self.menu.gameFrame.gamegrid
        self.grid.setSimulationPeriod(10)
        self.currentRound = 0
        self.roundActive = False

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

    def startRound(self, difficulty, roundnumber):
        print(difficulty, roundnumber)


Difficulty = enum(
    EASY=0,
    NORMAL=1,
    HARD=2
)

if __name__ == "__main__":
    game = Game()

    game.gameFrame.setVisible(True)
