from TowerBaseClass import * 

class Tower1(Tower):
    def __init__(self, placeX, placeY):
        
        Tower.__init__(self, 15, 5, 200, 250, placeX, placeY, 0.95, 1.15, "sprite.png")
        
    def upAR(self):
        self.aR += 20

        