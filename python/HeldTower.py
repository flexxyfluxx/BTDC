import syspaths
from jgamegrid import Actor

class HeldTower(Actor):
    def __init__(self, towerID):
        Actor.__init__(self, sprites[towerID])
        self.towerID = towerID
        self.xPos = 480
        self.yPos = 270