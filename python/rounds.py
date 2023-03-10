# -*- coding: utf-8 -*-

"""
Hier werden die Runden instantiiert und initialisiert.
"""

from round import Round, Wave

def getAllRounds(game):
    return [supplier(game) for supplier in ROUND_SUPPLIERS]

def getRound(id):
    return ROUND_SUPPLIERS[id]()

ROUND_SUPPLIERS = [
]

# Falls kein EXAMPLE_TYPE existiert: definieren als 0. Verwenden wir in dem Fall offensichtlich eh nicht.
# einfach nur, damit die IDE nicht allzu viele Fehler schmeißt lulw.
# Und falls tatsächlich n realer Gegnertyp EXAMPLE_TYPE existiert, ist ja alles gut;
# dann können wir einfach den verwenden.
try:
    EXAMPLE_TYPE
except NameError:
    EXAMPLE_TYPE = 0

# Orientierungsbeispiel für die Erstellung einer Runde.
EXAMPLE_ROUND = lambda game: (Round(game)
    .addWave(
        Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(10)
            .setSpacing(20)
            .setStartDelay(100)
    )
    .addWave(
        Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(15)
            .setSpacing(10)
            .setStartDelay(50)
            .setWaitsForLastRoundToBeFullySent()
    )
    .addWave(
        Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(7)
            .setSpacing(25)
            .setStartDelay(100)
    )
)
