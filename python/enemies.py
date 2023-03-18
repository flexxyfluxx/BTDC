# -*- coding: utf-8 -*-

from enemy import Enemy
from os.path import abspath

WEAKEST = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=1, health=1, speed=0.8,
    sprite=abspath("../assets/sprites/sprite.png"),  # TODO add proper sprite
    segmentIdx=segmentIdx, segmentProgress=segmentProgress
)

BLUE_EQ = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=2, health=1, speed=1.1,
    sprite=abspath("../assets/sprites/sprite2.png"),  # TODO add proper sprite
    segmentIdx=segmentIdx, segmentProgress=segmentProgress
)
