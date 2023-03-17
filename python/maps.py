# -*- coding=utf-8 -*-

"""
Hier werden alle Maps instantiiert.
"""

from maputil import Map
from de.wvsberlin.vektor import Vektor


RAUM208 = (Map()
               .setBgImg('../assets/maps/raum208v2.png')
               .setRelUpper(10)
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

RAUM208v1 = (Map()
               .setBgImg('../assets/maps/raum208v1.png')
               .setRelUpper(10)
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


theMaps = [
    RAUM208,
    RAUM208v1
]
