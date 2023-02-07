# -*- coding: utf-8 -*-
"""
Map-Modul: Enthält alles Notwendige, um Maps zu laden und zu verarbeiten.
"""

from de.wvsberlin import factory as fac
from ch.aplu.jgamegrid import GGBitmap
import json
from os.path import abspath


def validateNodes(nodes):
    """
    Überprüfe eine geg. Liste von Nodes auf Korrektheit.
    Falls korrekt: return True
    sonst: return False
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
            
            if not (0 <= num <= 1):
                print("[WARNING] at validateNodes: Number not in range(0,1). This could cause unexpected behavior!")

        node = tuple(node)  # zu Tupel umformen

    # keine Fehler gefunden
    return True


def loadMapFromJSON(filepath):
    """
    Erstelle aus einem geeigneten JSON-Dokument ein Map-Objekt.
    """
    with open(abspath(filepath)) as f:
        rawDict = json.load(f)

    return Map(rawDict.get("nodes"), rawDict.get("bg-path"))


class Map:
#class Map(fac.interfaces.MapType):  # für die Verwendung mit Kotlin
    def __init__(self, nodes, bgImgPath):
        if not validateNodes(nodes):
            raise TypeError("Invalid nodes!")

        self.nodes = nodes

        # Falls das Hintergrundbild fehlerhaft ist, schmeißt GGBitmap schon einen Fehler
        self.bgImg = GGBitmap.getImage(abspath(bgImgPath))

    def setGridBg(self, grid):
        """
        Setze den gespeicherten Hintergrund als Hintergrund des geg. Gamegrids.
        """
        grid.setBg(self.bgImg)


    def getDistToTrack(self, x, y):
        pass



