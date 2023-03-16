# -*- coding: utf-8 -*-
"""
Map-Modul: Enthält alles Notwendige, um Maps zu laden und zu verarbeiten.
"""

from __future__ import print_function

import syspaths
from de.wvsberlin import factory as fac
from ch.aplu.jgamegrid import GGBitmap
from os.path import abspath
from java.awt import Color
from java.awt.image import BufferedImage

from de.wvsberlin.vektor import Vektor


# class Map(fac.interfaces.MapType):  # für die Verwendung mit Kotlin, sofern nötig
class Map:  # ACHTUNG bei Instantiierung von Map-Objekten :: `map` wird schon von Python verwendet => don't use pls UwU
    def __init__(self):
        self.bgImg = None
        self.pathNodes = []

    def setBgImg(self, img):
        if isinstance(img, str):  # falls Filepath zu Bild gegeben...
            self.bgImg = GGBitmap.getImage(abspath(img))  # ...erstelle neues BufferedImage
        elif isinstance(img, BufferedImage):  # Falls schon BufferedImage:
            self.bgImg = img  # ...setze Attribut.
        elif img is None:
            pass
        else:
            raise TypeError("Image must either be supplied via filepath as string, or as java.awt.image.BufferedImage; %s given."
                            % type(img))

        return self

    def addNode(self, node):
        if not isinstance(node, Vektor):
            raise TypeError("Vektor required; %s given."
                            % type(node))
        else:
            self.pathNodes.append(node)
        return self

    def setRelUpper(self, num):
        try:
            float(num)
        except TypeError:
            raise TypeError("Number required; %s given."
                            % type(num))

        self._relUpper = num
        return self

    def setBgOfGrid(self, grid, debug=False):
        """
        Setze den gespeicherten Hintergrund als Hintergrund des geg. Gamegrids.
        """
        bg = grid.getBg()
        if self.bgImg is not None:
            bg.drawImage(self.bgImg)

        if debug:
            cellSize = grid.getCellSize()
            bgWidth = grid.getNbHorzCells() * cellSize
            bgHeight = grid.getNbVertCells() * cellSize

            bg.setPaintColor(Color.RED)
            bg.setLineWidth(3)

            xFactor = bgWidth / self.relUpper
            yFactor = bgHeight / self.relUpper

            for c in range(len(self._pathNodes) - 1):
                node0 = self._pathNodes[c]
                node1 = self._pathNodes[c + 1]
                bg.drawLine(node0.toPoint(xFactor, yFactor), node1.toPoint(xFactor, yFactor))

    def getDistToPath(self, pos):
        pass
