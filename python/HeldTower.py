import syspaths
from ch.aplu.jgamegrid import Actor
from os.path import abspath

class HeldTower(Actor):
    def __init__(self, towerID):
        self.sprites = [
            abspath('../assets/sprites/sprite.png'),
            abspath('../assets/sprites/sprite2.png'),
            abspath('../assets/sprites/denied.png')
        ]
        Actor.__init__(self, self.sprites)
        self.show(towerID)
        self.towerID = towerID
        self.xPos = 480
        self.yPos = 270