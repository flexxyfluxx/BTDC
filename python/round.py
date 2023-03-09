# -*- coding: utf-8 -*-

"""
Runden OwO
"""


class Round:
    def __init__(self):
        self.remainingWaves = []
        self.activeWaveFuns = []

        self.game = None

    def addWave(self, wave):
        self.remainingWaves.append(wave)
        return self

    def tick(self):
        if self.remainingWaves[0].startDelay <= 0:
            if not self.remainingWaves[0].waitsForLastRoundToBeFullySent:
                self.activeWaveFuns.append(lambda: self.remainingWaves.pop(0).tick(self.game))

            elif not self.activeWaveFuns:
                self.activeWaveFuns.append(self.remainingWaves.pop(0))

        else:
            self.remainingWaves[0].startDelay -= 1


class Wave:
    def __init__(self):
        self.startDelay = 0
        self.spacing = 0
        self.sendCooldown = 0
        self.count = 1
        self.enemyType = None  # todo replace w/ "normie" nme
        self.waitsForLastRoundToBeFullySent = False

    def setStartDelay(self, startDelay):
        """
        setze zeitlichen Intervall, bevor die Wave ausgeschickt wird
        """
        if not isinstance(startDelay, int):
            raise TypeError("Start Delay must be int; %s given."
                            % type(startDelay))
        if startDelay < 0:
            raise ValueError("Start Delay must not be less than zero.")

        self.startDelay = startDelay
        return self

    def setSpacing(self, spacing):
        """
        setze zeitlichen Intervall zw. den Gegnern einer Wave in Ticks
        """
        if not isinstance(spacing, int):
            raise TypeError("Spacing must be int; %s given."
                            % type(spacing))
        if spacing < 0:
            raise ValueError("Spacing must not be less than zero.")

        self.spacing = spacing
        return self

    def setCount(self, count):
        """
        setze Menge an Gegnern in der Wave
        """
        if not isinstance(count, int):
            raise TypeError("Count must be int; %s given."
                            % type(count))
        if count < 0:
            raise ValueError("Count must not be less than zero.")

        self.count = count
        return self

    def setEnemyType(self, enemyType):  # stub; requires enemy class
        """
        setze Art des Gegners
        """
        # TODO add type checking
        self.enemyType = enemyType
        return self

    def setWaitsForLastRoundToBeFullySent(self):
        """
        Stellt ein, dass die vorherige Runde komplett fertig ausgesandt werden muss, bevor der StartDelay beginnt.
        Dadurch können Waves auf Wunsch beliebig einfach und nahtlos aneinandergekettet werden.
        """
        self.waitsForLastRoundToBeFullySent = True
        return self

    def tick(self, game):
        pass
