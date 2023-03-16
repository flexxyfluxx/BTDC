from TowerBaseClass import * 

class Tower1(Tower):
    def __init__(self, placeX, placeY, key):
        
        Tower.__init__(self, 15, 5, 200, 250, placeX, placeY, 1.05, 1.15, abspath("../assets/sprites/sprite.png"), key)
        
    def upAR(self):
        self.aR += 20

        