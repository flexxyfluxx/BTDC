# -*- coding: utf-8 -*-

from enemy import Enemy
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap

WEAKEST = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=1, health=1, speed=1.5,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/ballon1.png"),0.8,0),  # TODO add proper sprite
    segmentIdx=segmentIdx, segmentProgress=segmentProgress
)

BLUE_EQ = lambda game, key, segmentIdx=0, segmentProgress=0: Enemy(
    game=game, key=key,
    dmg=2, health=1, speed=1.8,
    sprite=GGBitmap.getScaledImage(abspath("../assets/sprites/ballon2.png"),0.8,0),  # TODO add proper sprite
    segmentIdx=segmentIdx, segmentProgress=segmentProgress,
    childSupplier=WEAKEST
)
