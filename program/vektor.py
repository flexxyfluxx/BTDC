# -*- coding: utf-8 -*-
"""
Wrapper für das (kotlinbasierte) Vektor-Modul.
"""

from de.wvsberlin.vektor import *
from ch.aplu.jgamegrid import Location
from java.awt import Point


# Ich setze die Python-Operatorfunktionen so, um die rohen Funktionen nicht mitimportieren zu müssen.
def _setup():
    Vektor.toLocation = lambda self, xFac=1, yFac=1: Location(int(self.x * xFac), int(self.y * yFac))

    Vektor.toPoint = lambda self, xFac=1, yFac=1: Point(int(self.x * xFac), int(self.y * yFac))

    def _add(self, other):
        if not isinstance(other, Vektor):
            raise TypeError("Unexpected operand type %s; expected Vektor. (Can only add vectors to vectors!)"
                            % (type(other)))

        return Vektor(self.x + other.x, self.y + other.y)

    def _sub(self, other):
        if not isinstance(other, Vektor):
            raise TypeError("Unexpected operand type %s; expected Vektor. (Can only subtract vectors from vectors!)"
                            % (type(other)))

        return Vektor(self.x - other.x, self.y - other.y)

    def _mul(self, other):
        if isinstance(other, Vektor):
            return self.x * other.x + self.y * other.y

        try:
            float(other)  # check if other is a num
        except TypeError:
            raise TypeError("Unexpected operand type %s; expected Float/Int or Vektor."
                            % (type(other)))

        return Vektor(self.x * other, self.y * other)

    def _div(self, other):
        try:
            float(other)
        except TypeError:
            raise TypeError("Unexpected operand type %s; expected Float or Int. (Can only divide vectors by numbers!)"
                            % (type(other)))

        return Vektor(self.x / other, self.y / other)

    # Mutability ist ne schöne Sache.
    Vektor.__add__ = _add
    Vektor.__sub__ = _sub
    Vektor.__div__ = _div
    Vektor.__mul__ = Vektor.__rmul__ = _mul


if __name__ == "vektor":
    _setup()
    del _setup  # Wir brauchen nach Import keine Setup-Funktion mehr.
