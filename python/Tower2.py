from TowerBaseClass import * 

class Tower2(Tower):
    def __init__(self, placeX, placeY, key):
        
        Tower.__init__(self, 400, 400, 300, 300, placeX, placeY, 1.01, 1.3, abspath("../assets/sprites/sprite2.png"), key)
    
    def sonderenemyzerstoerer(self):
        pass

    def getClosestEnemy(self):
        raise NotImplementedError("This function has not been implemented yet!")