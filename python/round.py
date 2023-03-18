# -*- coding: utf-8 -*-

"""
Runden OwO
"""
from java.lang import IllegalStateException


class Round:
    def __init__(self, game):
        # ideally assign-once; never assign again
        self.waves = []

        # volatile: init, then watch it destroy itself, and do it again.
        self.activeWaves = []

        self.game = game

    def addWave(self, waveSupplier):
        self.waves.append(waveSupplier())
        return self

    def tick(self):
        if not self.waves:
            raise StopIteration
            # Da dies der offizielle, pythonische Weg ist, Iterations zu beenden, tun wir dies auch.
            # try-except wowowowowow

        if self.waves[0].startDelay <= 0:
            if not self.waves[0].waitsForLastRoundToBeFullySent:
                newWave = self.waves.pop(0)
                self.activeWaves.append(newWave)

            elif not self.activeWaves:
                self.activeWaves.append(self.waves.pop(0))

        else:
            self.waves[0].startDelay -= 1

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
        if self.sendCooldown <= 0:
            game.spawnEnemy(self.enemyType)
