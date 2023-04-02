# -*- coding=utf-8 -*-

"""
Hier werden alle Maps instantiiert.
"""

import syspaths
from os.path import abspath
from ch.aplu.jgamegrid import GGBitmap
from de.wvsberlin import Map
from de.wvsberlin.vektor import Vektor


RAUM208_V2 = (Map()
    .setBgImg(GGBitmap.getImage(abspath('../assets/maps/raum208v2.png')))
    .addNode(Vektor( 862, 540 ))
    .addNode(Vektor( 862, 455 ))
    .addNode(Vektor( 655, 455 ))
    .addNode(Vektor( 655, 233 ))
    .addNode(Vektor( 610, 233 ))
    .addNode(Vektor( 610, 455 ))
    .addNode(Vektor( 502, 455 ))
    .addNode(Vektor( 502, 233 ))
    .addNode(Vektor( 455, 233 ))
    .addNode(Vektor( 455, 455 ))
    .addNode(Vektor( 335, 455 ))
    .addNode(Vektor( 335, 233 ))
    .addNode(Vektor( 287, 233 ))
    .addNode(Vektor( 287, 455 ))
    .addNode(Vektor( 135, 455 ))
    .addNode(Vektor( 135, 103 ))
    .addNode(Vektor( 960, 103 ))
)

RAUM208_LEGACY = (Map()
    .setBgImg(GGBitmap.getImage(abspath('../assets/maps/raum208v1.png')))
    .addNode(Vektor(840,539))
    .addNode(Vektor(840,453))
    .addNode(Vektor(577,453))
    .addNode(Vektor(577,216))
    .addNode(Vektor(532,216))
    .addNode(Vektor(532,453))
    .addNode(Vektor(444,453))
    .addNode(Vektor(444,220))
    .addNode(Vektor(400,220))
    .addNode(Vektor(400,453))
    .addNode(Vektor(295,453))
    .addNode(Vektor(295,221))
    .addNode(Vektor(250,221))
    .addNode(Vektor(250,453))
    .addNode(Vektor(155,453))
    .addNode(Vektor(155,135))
    .addNode(Vektor(959,135))
)

JUNGLE = (Map()
    .setBgImg(GGBitmap.getImage(abspath('../assets/maps/jungle.png')))
    .addNode(Vektor(351, 520))
    .addNode(Vektor(406, 483))
    .addNode(Vektor(201, 407))
    .addNode(Vektor(109, 321))
    .addNode(Vektor(89, 209))
    .addNode(Vektor(28, 142))
    .addNode(Vektor(114, 112))
    .addNode(Vektor(125, 63))
    .addNode(Vektor(376, 86))
    .addNode(Vektor(565, 36))
    .addNode(Vektor(545, 113))
    .addNode(Vektor(668, 111))
    .addNode(Vektor(674, 192))
    .addNode(Vektor(793, 245))
    .addNode(Vektor(795, 290))
)

theMaps = [
    RAUM208_V2,
    RAUM208_LEGACY,
    JUNGLE
]
