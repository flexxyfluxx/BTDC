# -*- coding=utf-8 -*-

from ch.aplu.jgamegrid import Actor
from maps import theMaps


class Enemy(Actor):
    def __init__(self, dmg, health, speed):
        Actor.__init__(self, "sprite") 
        self.dmg = dmg
        self.health = health
        self.speed = speed
        self.theMap = theMaps[0]
        
    def startLocation(self): #setzt den Enemy auf die Startkoordinate des Pfades
        self.setLocation(Vektor(self.theMap.pathNodes[0]))
        
    def nextLocation(self): #setzt den Enemy auf die nächste Koordinate des Pfades
        for node in self.theMap.pathNodes:
            self.setLocation(Vektor(self.theMap.pathNodes[Vektor(node)]))    
        
    def act(self):
        self.startLocation()
        self.nextLocation()
    
    
    

        