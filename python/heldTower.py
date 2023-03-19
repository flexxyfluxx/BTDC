# -*- coding=utf-8 -*-

import syspaths
from ch.aplu.jgamegrid import Actor
from os.path import abspath
from de.wvsberlin.vektor import Vektor


class HeldTower(Actor): # hilfsklasse zum anzeigen des zu plazierenden Towers
    def __init__(self, towerID):
        self.sprites = [
            abspath('../assets/sprites/crTower.png'),
            abspath('../assets/sprites/egirlsheesh.png'),
            abspath('../assets/sprites/picasso.png'),
            abspath('../assets/sprites/spriteDebug.png'),
            abspath('../assets/sprites/denied.png')
        ]
        Actor.__init__(self, self.sprites)
        self.show(towerID)
        self.towerID = towerID
        self.pos = Vektor(480, 270)
