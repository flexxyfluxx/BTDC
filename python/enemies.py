# -*- coding: utf-8 -*-

from enemy import Enemy
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap

WEAKEST = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=1, health=1, speed=1.5,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/ballon1.png"), 0.8, 0),
    segmentIdx=segmentIdx, segmentProgress=segmentProgress
)

BLUE = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=2, health=1, speed=2,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/ballon2.png"), 0.8, 0),
    segmentIdx=segmentIdx, segmentProgress=segmentProgress,
    childSupplier=WEAKEST
)

GREEN = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=3, health=1, speed=3,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.8, 0),
    segmentIdx=segmentIdx, segmentProgress=segmentProgress,
    childSupplier=BLUE
)

YELLOW = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=4, health=1, speed=5,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.8, 0),
    segmentIdx=segmentIdx, segmentProgress=segmentProgress,
    childSupplier=GREEN
)

PINK = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=5, health=1, speed=8,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/spritesprite.png"), 0.8, 0),
    segmentIdx=segmentIdx, segmentProgress=segmentProgress,
    childSupplier=YELLOW
)
