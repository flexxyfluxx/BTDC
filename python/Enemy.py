from ch.aplu.jgamegrid import Actor
from maps import theMaps


class Enemy(Actor):
    def __init__(self, dmg, health, speed):
        Actor.__init__(self, "sprite") 
        self.dmg = dmg
        self.health = health
        self.speed = speed

    def act(self):
        self.startLocation(startLoc)
        self.setLocation(setLoc)
        
        
        pass