# -*- coding: utf-8 -*-

"""
Runden OwO
"""
from enemies import WEAKEST
from de.wvsberlin import Counter


class Round:
    def __init__(self, game):
        # ideally assign-once; never assign again
        self.waves = []

        # volatile: init, then watch it destroy itself, and do it again.
        self.activeWaves = {}

        self.game = game
        self.waveKeyGen = Counter()

    def addWave(self, waveSupplier):
        key = next(self.waveKeyGen)
        self.waves.append(waveSupplier(key))
        return self

    def tick(self): # eigene tick funktion, damit auch außerhalb der runden noch mit dem gamegrid interagiert werden kann
        if not (self.waves or self.activeWaves):
            raise StopIteration
            # Da dies der offizielle, pythonische Weg ist, Iterations zu beenden, tun wir dies auch.
            # try-except wowowowowow

        if self.waves:
            if self.waves[0].startDelay <= 0:
                if not self.waves[0].waitsForLastRoundToBeFullySent:
                    newWave = self.waves.pop(0)
                    self.activeWaves[newWave.key] = newWave

                elif not self.activeWaves:
                    newWave = self.waves.pop(0)
                    self.activeWaves[newWave.key] = newWave

            else:
                self.waves[0].startDelay -= 1

        for wave in self.activeWaves.values():
            if wave.count <= 0:
                self.activeWaves.pop(wave.key)
                continue

            wave.tick()

    def addReward(self, reward): # Geld, das beim Abschluss der Runde gutgeschrieben wird
        self.reward = reward
        return self


class Wave:
    def __init__(self, game, key):
        self.game = game
        self.key = key
        self.startDelay = 0
        self.spacing = 0
        self.sendCooldown = 0
        self.count = 1
        self.enemyType = WEAKEST
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

    def tick(self): # eigene tick funktion, damit auch außerhalb der runden noch mit dem gamegrid interagiert werden kann
        if self.sendCooldown > 0:
            self.sendCooldown -= 1
            return

        self.count -= 1
        self.game.spawnEnemy(self.enemyType)
        self.sendCooldown = self.spacing
