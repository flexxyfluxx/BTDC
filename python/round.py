# -*- coding: utf-8 -*-

"""
Runden OwO
"""

from __future__ import print_function
from os.path import abspath


class Round:
    def __init__(self):
        self.currentRound = 0
        self.waves = []


class Wave:
    """
    Wave:
    Folgendermaßen zu instantiieren:
    myWave = Wave() \
        # und dann beliebig die verfügbaren Modifiers aneinanderketten, zB:
        .startDelay(12) \
        .spacing(3) \
        .count(1073741824) \
        .waitsForLastRoundToBeFullySent()
        # die Reihenfolge ist natürlich egal.
    """
    def __init__(self):
        self._startDelay = 0
        self._spacing = 0
        self._count = 1
        self._type = None  # todo replace w/ "normie" nme
        self._waitsForLastRoundToBeFullySent = False

    def startDelay(self, startDelay):
        """
        setze zeitlichen Intervall, bevor die Wave ausgeschickt wird
        """
        if not isinstance(startDelay, int):
            raise TypeError("Start Delay must be int; %s given."
                            % type(startDelay))
        if startDelay < 0:
            raise ValueError("Start Delay must not be less than zero.")

        self._startDelay = startDelay
        return self

    def spacing(self, spacing):
        """
        setze zeitlichen Intervall zw. den Gegnern einer Wave in Ticks
        """
        if not isinstance(spacing, int):
            raise TypeError("Spacing must be int; %s given."
                            % type(spacing))
        if spacing < 0:
            raise ValueError("Spacing must not be less than zero.")

        self._spacing = spacing
        return self

    def count(self, count):
        """
        setze Menge an Gegnern in der Wave
        """
        if not isinstance(count, int):
            raise TypeError("Count must be int; %s given."
                            % type(count))
        if count < 0:
            raise ValueError("Count must not be less than zero.")

        self._count = count

    def type(self, type_):  # stub; requires enemy class
        """
        setze Art des Gegners
        """
        return self

    def waitsForLastRoundToBeFullySent(self):
        """
        Stellt ein, dass die vorherige Runde komplett fertig ausgesandt werden muss, bevor der StartDelay beginnt.
        Dadurch können Waves auf Wunsch beliebig einfach und nahtlos aneinandergekettet werden.
        """
        self._waitsForLastRoundToBeFullySent = True
        return self
