# -*- coding: utf-8 -*-

"""
Runden OwO
"""
from java.lang import IllegalStateException


class Round:
    def __init__(self):
        # ideally assign-once; never assign again
        self.waveSuppliers = []

        # volatile: init, then watch it destroy itself, and do it again.
        self.remainingWaves = []
        self.activeWaves = []

        self.game = None

    def initRound(self, game):
        """
        Binde die Runde an ein Game-Objekt.
        """
        self.game = game
        self.remainingWaves = [waveSupplier() for waveSupplier in self.waveSuppliers]

    def deinitRound(self):
        """
        Entkopple die Runde vom Game-Objekt und leere die vergänglichen Arrays.
        """
        self.game = None
        self.remainingWaves = []
        self.activeWaves = []

    def addWave(self, waveSupplier):
        self.waveSuppliers.append(waveSupplier)
        return self

    def tick(self):
        if self.game is None:
            raise IllegalStateException("Cannot call tick: Round not initialized! \nCall self.initRound to initialize.")
        if not self.remainingWaves:
            raise StopIteration
            # Da dies der offizielle, pythonische Weg ist, Iterations zu beenden, tun wir dies auch.
            # try-except wowowowowow

        if self.remainingWaves[0].startDelay <= 0:
            if not self.remainingWaves[0].waitsForLastRoundToBeFullySent:
                newEnemy = self.remainingWaves.pop(0)
                self.game.spawnEnemy(newEnemy)
                self.activeWaves.append(newEnemy)

            elif not self.activeWaves:
                self.activeWaves.append(self.remainingWaves.pop(0))

        else:
            self.remainingWaves[0].startDelay -= 1

        for wave in self.activeWaves:
            wave.tick(self.game)


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
