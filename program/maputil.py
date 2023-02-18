# -*- coding: utf-8 -*-
"""
Map-Modul: Enthält alles Notwendige, um Maps zu laden und zu verarbeiten.
"""

from __future__ import print_function

from de.wvsberlin import factory as fac
from ch.aplu.jgamegrid import GGBitmap
import json
from os.path import abspath
from java.awt import Color

import vektor as vek

__all__ = [
    "ParseError",
    "validateNodes",
    "validateRawNodes",
    "loadMapFromJSON",
    "Map"
]


class ParseError(Exception):
    """ Kommt vor, wenn die Erstellung einer Map aus einer JSON-Quelldatei fehlschlägt. """
    pass


def validateRawNodes(nodes):
    """
    Überprüfe eine geg. Liste von Nodes (als Liste mit 2 Elementen) auf Korrektheit.
    """
    if len(nodes) < 2:  # nicht genug Nodes
        return False


    for node in nodes:
        try:
            if len(node) != 2:  # keine 2d-Koordinate
                return False

        except TypeError:  # überhaupt keine Sache, die Elemente enthält
            return False

        if type(node) == str:  # Theoretisch kommt ein zweistelliger String von Zahlen durch (nicht gewollt)
            return False

        # Es ist nun klar, dass es sich um ein Paar von Elementen handelt.
        for num in node:
            # prüfen, ob beide Elemente Zahlen sind:
            try:
                float(num)  # coerce to Float
            except TypeError:  # lässt sich nicht als Float darstellen
                return False
            
        node = tuple(node)  # zu Tupel umformen

    # keine Fehler gefunden
    return True

def printAndReturn(anything):
    print(anything)
    return anything


def validateNodes(nodes):
    """
    Überprüfe eine geg. Liste von Nodes (als Vektoren) auf Korrektheit.
    """
    if len(nodes) < 2:  # nicht genug Nodes
        return False

    return all(map(nodes, lambda node: isinstance(node, vek.Vektor)))


def loadMapFromJSON(filepath):
    """
    Erstelle aus einem geeigneten JSON-Dokument ein Map-Objekt.
    """

    fullFilePath = abspath(filepath)
    with open(fullFilePath) as f:
        rawMap = json.load(f)
    
    print("rawMap =", rawMap)

    # sichergehen, dass alle notwendigen Elemente enthalten sind
    if not rawMap.__contains__("nodes"):
        raise ParseError("JSON file '%s' has no element 'nodes'." % (fullFilePath))
    
    if rawMap.__contains__("bg-path"):
        bgImg = abspath(rawMap["bgImg"])
    else:
        bgImg = None

    rawNodes = rawMap["nodes"]
    print("rawNodes =", rawNodes)

    if not validateRawNodes(rawNodes):
        raise ParseError("Nodes in JSON file '%s' have improper format and cannot be parsed." % (fullFilePath))

    nodes = [vek.Vektor(printAndReturn(node)[0], node[1]) for node in rawNodes]

    if rawMap.__contains__("upper"):
        scale = rawMap["upper"]
    else:
        scale = 1

    return Map(nodes, bgImg, srcUpper=scale)


class Map:
#class Map(fac.interfaces.MapType):  # für die Verwendung mit Kotlin
    def __init__(self, nodes, bgImgPath, srcUpper=1, relUpper=1):
        print("nodes =", nodes)

        # self.nodes = list(map(list(nodes), lambda node: node / srcUpper * relUpper))
        self.nodes = [node / srcUpper * relUpper for node in nodes]

        self.relUpper = relUpper

        # Falls das Hintergrundbild fehlerhaft ist, schmeißt GGBitmap schon einen Fehler
        if bgImgPath is None:
            self.bgImg = None
        else:
            self.bgImg = GGBitmap.getImage(abspath(bgImgPath))

    def setGridBg(self, grid, debug=False):
        """
        Setze den gespeicherten Hintergrund als Hintergrund des geg. Gamegrids.
        """
        if self.bgImg is not None:
            grid.setBg(self.bgImg)

        if debug:
            bg = grid.getBg()
            cellsize = grid.getCellSize()
            bgWidth = grid.getNbHorzCells() * cellsize
            bgHeight = grid.getNbVertCells() * cellsize

            bg.setPaintColor(Color.RED)
            bg.setLineWidth(2)

            xFactor = bgWidth / self.relUpper
            yFactor = bgHeight / self.relUpper

            for c in range(len(self.nodes) - 1):
                node0 = self.nodes[c]
                node1 = self.nodes[c + 1]
                bg.drawLine(node0.toPoint(xFactor, yFactor), node1.toPoint(xFactor, yFactor))


    def getDistToTrack(self, x, y):
        pass



