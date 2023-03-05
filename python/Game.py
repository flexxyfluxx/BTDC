from JGameFrame import *
from maputil import *
#from TowerBaseClass import *

class Game():
    def __init__(self):
        self.gameframe = JGameFrame(self.buttonStartGame, self.buttonStartNextRound)
        self.grid = self.gameframe.gamegrid
        self.grid.setSimulationPeriod(10)
    
    def startGame(self, difficulty): #Difficulty 0 = easy; 1 = normal, 2 = hard
        if difficulty == 0:
            self.health = 100
            self.money = 1000
        elif difficulty == 1:
            self.health = 50
            self.money = 500
        elif difficulty == 2:
            self.health = 1
            self.money = 250
        else:
            raise ValueError("Illegal difficulty")
    
    def startRound(self, difficulty, roundnumber): 
        #[@FLEX]
        print(difficulty, roundnumber)
        #pass
    
    def buttonStartNextRound(self, event):
        self.startRound(self.difficulty, self.currentround)
        
    def buttonStartGame(self, event):
        #implement map loader
        self.difficulty = self.gameframe.getSelectedDifficulty()
        self.startGame(self.difficulty)
        self.currentRound = 1
    



    #6 Buttons der Tower function