# -*- coding: utf-8 -*-
"""
Projektile UwU
"""

from ch.aplu.jgamegrid import Actor
import vektor as vek


class Projectile(Actor):
    """
    Ein Projektil, das selbstständig in eine Richtung weiterfliegt.
    Hat eine spezifische Lifetime, die in Ticks gegeben wird.
    """

    def __init__(self, richtungsvektor, sprite, lifetime, initpos=vek.Vektor.NullVektor):
        if not isinstance(richtungsvektor, vek.Vektor):
            raise TypeError("First (non-self) argument must be of type Vektor; %s given."
                            % type(richtungsvektor))

        try:
            self.lifetime = int(lifetime)
            if self.lifetime < 0:
                raise TypeError
        except TypeError:
            raise TypeError("Second (non-self) argument must be coercible to int; %s given."
                            % type(lifetime))

        if not isinstance(initpos, vek.Vektor):
            raise TypeError("Third (non-self) argument must be of type Vektor; %s given."
                            % type(initpos))

        Actor.__init__(self, sprite)
        self.rvek = richtungsvektor
        self.pos = initpos

    def act(self):
        # Falls Tick Counter leer, despawnen.
        if self.lifetime <= 0:
            self.despawn()

        self.lifetime -= 1

        self.pos += self.rvek
        self.setLocation(self.pos.toLocation())

    def despawn(self):
        # falls in einem Gamegrid vorhanden: entfernen.
        if self.gameGrid is not None:
            self.gameGrid.removeActor(self)

        del self


if __name__ == "__main__":
    import syspaths
    from ch.aplu.jgamegrid import GameGrid, GGBitmap
    from os.path import abspath

    grid = GameGrid(600, 600)
    proj = Projectile(vek.Vektor(1, 1), GGBitmap.getScaledImage(abspath("../assets/sprites/sprite.png")), 500)

    grid.addActor(proj)
    grid.show()
    grid.setSimulationPeriod(10)
