# -*- coding: utf-8 -*-

import syspaths
from projectile import Projectile
from de.wvsberlin.vektor import Vektor
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap

EXAMPLE_PROJ = lambda game, direction, pos, lifetime: Projectile(
    game=game,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.1, 0),
    lifetime=lifetime, pierce=1, size=6, pos=pos
)

TOWER1_PROJ = lambda game, direction, pos, lifetime: Projectile(
    game = game,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath('../assets/sprites/spritesprite.png'), 0.1, 0),
    lifetime=lifetime, pierce=1, size=6, pos=pos
)

TOWER2_PROJ = lambda game, direction, pos, lifetime: Projectile(
    game = game,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=GGBitmap.getScaledImage(abspath('../assets/sprites/spritesprite.png'), 0.1, 0),
    lifetime=lifetime, pierce=1, size=6, pos=pos
)