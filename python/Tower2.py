from TowerBaseClass import * 

class Tower1(Tower):
    def __init__(self, placeX, placeY):
        
        Tower.__init__(self, 400, 400, 300, 300, placeX, placeY, 0.99, 1.3, "sprite2.png", 150, 300)
    
    def sonderenemyzerstörer(self):
        def getClosestEnemy(self):
            raise NotImplementedError("This function has not been implemented yet!")