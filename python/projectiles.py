# -*- coding: utf-8 -*-

import syspaths
from projectile import Projectile
from de.wvsberlin.vektor import Vektor
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap

EXAMPLE_PROJ = lambda game, key, direction, pos, lifetime, pierce: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.1, ),
    lifetime=100, pierce=pierce, size=6, pos=pos
)

TOWER1_PROJ = lambda game, key, direction, pos, lifetime, pierce: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/arrows.png"), 0.1, direction-90),
    lifetime=lifetime, pierce=pierce, size=6, pos=pos
)

TOWER2_PROJ = lambda game, key, direction, pos, lifetime, pierce: Projectile(
    game=game, key=key,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/uwu.png"), 0.1, 0),
    lifetime=lifetime, pierce=pierce, size=6, pos=pos
)


if __name__ == "__main__":
    pass