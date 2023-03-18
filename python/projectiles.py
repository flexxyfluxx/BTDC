# -*- coding: utf-8 -*-

import syspaths
from projectile import Projectile
from de.wvsberlin.vektor import Vektor
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap

EXAMPLE_PROJ = lambda game, key, direction, pos: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.1, 0),
    lifetime=100, pierce=1, size=6, pos=pos
)

TOWER1_PROJ = lambda game, key, direction, pos, lifetime: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.1, 0),
    lifetime=lifetime, pierce=1, size=6, pos=pos
)

TOWER2_PROJ = lambda game, key, direction, pos, lifetime: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.1, 0),
    lifetime=lifetime, pierce=1, size=6, pos=pos
)


if __name__ == "__main__":
    from ch.aplu.jgamegrid import GameGrid
    from java.awt import Color
    grid = GameGrid(600, 600)

    center = Vektor(300, 300)
    angle = 315
    angleVek = Vektor.fromAngleAndMagnitude(angle, 200)
    print(angleVek.getAngle())

    proj = EXAMPLE_PROJ(None, 0, angle, center)

    proj.act = proj.tick

    grid.addActor(proj, center.toLocation())

    bg = grid.getBg()
    bg.setPaintColor(Color.RED)
    bg.drawLine(center.toPoint(), (center + angleVek).toPoint())

    grid.setSimulationPeriod(20)
    grid.show()
    grid.doRun()
