from JGameFrame import *

class Game():
    def __init__(self):
        self.gameframe = JGameFrame(FENSTERBREITE, FENSTERHOEHE)
        self.grid = self.gameframe.grid
        self.setSimulationPeriod(10);
        self.currency = 0
        
    
    
       
    def buttonStartGameOnClick(event):
        
        def methode():
            self.currency += 1
            