# -*- coding=utf-8 -*-

"""
Hier werden alle Maps instantiiert.
"""

from maputil import Map
from de.wvsberlin.vektor import Vektor


MAP_EXAMPLE = (Map()
               .setBgImg(None)
               .setRelUpper(10)
               .addNode(Vektor( 0 , 2 ))
               .addNode(Vektor( 4 , 2 ))
               .addNode(Vektor( 4 , 8 ))
               .addNode(Vektor( 6 , 8 ))
               .addNode(Vektor( 6 , 4 ))
               .addNode(Vektor( 10, 4 ))
               )
MAP_R208 = (Map()
            .setBgImg(None)
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

theMaps = [
    MAP_EXAMPLE,
]