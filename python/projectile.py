# -*- coding: utf-8 -*-
"""
Projektile UwU
"""

from ch.aplu.jgamegrid import Actor
from de.wvsberlin.vektor import Vektor, MutableVektor
from java.lang import IllegalStateException


class Projectile(Actor):
    """
    Ein Projektil, das selbstständig in eine Richtung weiterfliegt.
    Hat eine spezifische Lifetime, die in Ticks gegeben wird.
    Wenn diese Lifetime endet, verschwindet das Projektil sofort.
    """

    def __init__(self, game, key, richtungsvektor, sprite, lifetime, pierce, size, pos):
        self.game = game
        self.key = key
        if not (isinstance(richtungsvektor, Vektor) or isinstance(richtungsvektor, MutableVektor)):
            raise TypeError("First (non-self) argument must be of type Vektor; %s given."
                            % type(richtungsvektor))

        try:
            self.lifetime = int(lifetime)
            if self.lifetime < 0:
                raise ValueError("Second (non-self) argument must not be less than zero!")
        except TypeError:
            raise TypeError("Second (non-self) argument must be coercible to int; %s given."
                            % type(lifetime))

        # this is so very very wet, but idffk how to make this into a function
        # (same goes for all the other typechecking boilerplate)
        try:
            self.pierce = int(pierce)
            if self.pierce < 0:
                raise ValueError("Third (non-self) argument must not be less than zero!")
        except TypeError:
            raise TypeError("Third (non-self) argument must be coercible to int; %s given."
                            % type(pierce))

        try:
            self.size = float(size)
            if self.size < 0:
                raise ValueError("Fourth (non-self) argument must not be less than zero!")
        except TypeError:
            raise TypeError("Fourth (non-self) argument must be coercible to int; %s given."
                            % type(size))

        if isinstance(pos, MutableVektor):
            self.pos = pos
        else:
            self.pos = MutableVektor.fromImmutable(pos)

        Actor.__init__(self, True, sprite)
        self.richtungsvektor = richtungsvektor
        self.setDirection(self.richtungsvektor.getAngle())

        self.enemiesHit = []

    def tick(self):
        # Falls Tick Counter leer, despawnen.
        if self.lifetime <= 0 or self.pierce <= 0:
            self.despawn()
            return

        self.lifetime -= 1

        self.pos += self.richtungsvektor
        self.setLocation(self.pos.toLocation())

        #for enemy in self.getTouchedEnemies():
        #    if enemy not in self.enemiesHit:
        #        self.enemiesHit.append(enemy)
        #        self.onEnemyTouched(enemy)

    def despawn(self):
        # falls in einem Gamegrid vorhanden: entfernen.
        if self.gameGrid is not None:
            self.gameGrid.removeActor(self)

        self.game.activeProjectiles.pop(self.key)

    def isTouchingEnemy(self, enemy):  # stub
        if Vektor.dist(self.pos, enemy.pos, False) < (self.size + enemy.size) ** 2 and enemy not in self.enemiesHit:
            return True
        return False

    def onEnemyTouched(self, enemy):  # stub
        # TODO implement this
        raise NotImplementedError("This function has not been implemented yet!")

    def getDirection(self):
        return self.richtungsvektor.getAngle()


if __name__ == "__main__":
    import syspaths
    from ch.aplu.jgamegrid import GameGrid, GGBitmap, Location
    from os.path import abspath

    grid = GameGrid(600, 600)
    proj = Projectile(Vektor(1, 1), GGBitmap.getScaledImage(abspath("../assets/sprites/sprite.png"), 0.1, 0), 500)

    grid.addActor(proj, Location())
    grid.show()
    grid.setSimulationPeriod(10)

    grid.doRun()
