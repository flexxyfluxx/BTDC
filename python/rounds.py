# -*- coding: utf-8 -*-

"""
Hier werden die Runden instantiiert und initialisiert.
"""

from round import Round, Wave


# Falls kein EXAMPLE_TYPE existiert: definieren als 0. Verwenden wir in dem Fall offensichtlich eh nicht.
# einfach nur, damit die IDE nicht allzu viele Fehler schmeißt lulw.
# Und falls tatsächlich n realer Gegnertyp EXAMPLE_TYPE existiert, ist ja alles gut;
# dann können wir einfach den verwenden.
try:
    EXAMPLE_TYPE
except NameError:
    EXAMPLE_TYPE = 0

# Orientierungsbeispiel für die Erstellung einer Runde.
EXAMPLE_ROUND = (Round()
    .addWave(
        lambda: Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(10)
            .setSpacing(20)
            .setStartDelay(100)
    )
    .addWave(
        lambda: Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(15)
            .setSpacing(10)
            .setStartDelay(50)
            .setWaitsForLastRoundToBeFullySent()
    )
    .addWave(
        lambda: Wave()
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(7)
            .setSpacing(25)
            .setStartDelay(100)
    )
)
