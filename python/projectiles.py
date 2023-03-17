# -*- coding: utf-8 -*-

import syspaths
from projectile import Projectile
from de.wvsberlin.vektor import Vektor
from os.path import abspath

EXAMPLE_PROJ = lambda game, direction, pos: Projectile(
    game=game,
    richtungsvektor=Vektor.fromAngleAndMagnitude(direction, 5),
    sprite=abspath("../assets/sprites/sprite.png"),
    lifetime=1, pierce=1, size=6, pos=pos
)
