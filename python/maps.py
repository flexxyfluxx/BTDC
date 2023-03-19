# -*- coding=utf-8 -*-

"""
Hier werden alle Maps instantiiert.
"""

from maputil import Map
from de.wvsberlin.vektor import Vektor


RAUM208_V2 = (Map()
    .setBgImg('../assets/maps/raum208v2.png')
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

RAUM208_V1 = (Map()
    .setBgImg('../assets/maps/raum208v1.png')
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

MAP_Example = (Map()
    .setBgImg(None)
    .addNode(Vektor( 0 , 2 ))
    .addNode(Vektor( 4 , 2 ))
    .addNode(Vektor( 4 , 8 ))
    .addNode(Vektor( 6 , 8 ))
    .addNode(Vektor( 6 , 4 ))
    .addNode(Vektor( 10, 4 ))
)


theMaps = [
    RAUM208_V2,
    RAUM208_V1,
    MAP_Example
]
