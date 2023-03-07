# -*- coding=utf-8 -*-

"""
Hier werden alle Maps instantiiert.
"""

from maputil import Map
from de.wvsberlin.vektor import Vektor


MAP_EXAMPLE = (Map()
               .bgImg(None)
               .relUpper(10)
               .addNode(Vektor( 0 , 2 ))
               .addNode(Vektor( 4 , 2 ))
               .addNode(Vektor( 4 , 8 ))
               .addNode(Vektor( 6 , 8 ))
               .addNode(Vektor( 6 , 4 ))
               .addNode(Vektor( 10, 4 ))
               )


theMaps = [
    MAP_EXAMPLE,
]
