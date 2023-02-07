# coding=utf-8
"""
Gleichungen und Datentypen rund um die Vektorrechnung.
"""

from math import sqrt


class Vektor:
#class Vektor(fac.interfaces.VektorType):
    def __init__(self, x1,x2):
        # Falls die Parameter keine Zahlen sind, wird die Umwandlung zu Float einen Fehler schmeißen (dies ist gewollt.)
        self.x1 = float(x1)
        self.x2 = float(x2)


    def __str__(self):
        if self.x2 < 0:
            return "%fi - %fj" \
                % (self.x1, abs(self.x2))

        return "%fi + %fj" \
            % (self.x1, self.x2)


    def __repr__(self):
        return "<Vektor(%f, %f) object in %s at 0x%x>" \
                % (self.x1, self.x2, __name__, id(self))


    def __eq__(self, other):
        if self.x1 == other.x1 and self.x2 == other.x2 and isinstance(other, Vektor):
            return True
        return False


    def __ne__(self, other):
        return not self.__eq__(other)


    def __add__(self, other):
        if isinstance(other, int) or isinstance(other, float):
            return Vektor(self.x1 * other, self.x2 * other)

        if isinstance(other, Vektor):
            return Vektor(self.x1 + other.x1, self.x2 + other.x2)

        raise TypeError("Can only multiply with a number or another vector!")


    def __sub__(self, other):
        return Vektor(self.x1 - other.x1, self.x2 - other.x2)
    

    def __mul__(self, other):  # Skalarprodukt
        if isinstance(other, Vektor): 
            return self.x1 * other.x1 + self.x2 + other.x2

        return Vektor(self.x1 * other, self.x2 * other)

    # Kein Kreuzprodukt, da nicht mathematisch möglich mit 2-dimensionalen Vektoren.


    def __rmul__(self, other):
        return self.__mul__(other)


    def __truediv__(self, other):
        if isinstance(other, int) or isinstance(other, float):
            return Vektor(self.x1 / other, self.x2 / other)

        raise TypeError("Can only divide by a scalar (a real number).")


    def __abs__(self):
        return sqrt(self.x1**2 + self.x2**2)



def vecdist(v1, v2):
    """
    Abstand zweier Punkte als Ortsvektoren.
    """
    if not (isinstance(v1, Vektor) and isinstance(v2, Vektor)):
        raise TypeError("Arguments must be vectors!")

    return abs(v1 - v2)


def vecnormal(v):
    if not isinstance(v, Vektor):
        raise TypeError("Can only get normal vector of a vector!")

    return Vektor(v.x2, -v.x1)




