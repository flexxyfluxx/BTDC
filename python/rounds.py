# -*- coding: utf-8 -*-

"""
Hier werden die Runden instantiiert und initialisiert.
"""

from round import Round, Wave
from enemies import *


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
    .addWave(lambda key: Wave(game, key)
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(10)
            .setSpacing(20)
            .setStartDelay(100)
    )
    .addWave(lambda key_: Wave(game, key_)
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(15)
            .setSpacing(10)
            .setStartDelay(50)
            .setWaitsForLastRoundToBeFullySent()
    )
    .addWave(lambda key_: Wave(game, key_)
            .setEnemyType(EXAMPLE_TYPE)
            .setCount(7)
            .setSpacing(25)
            .setStartDelay(100)
    )
)


ROUNDS = lambda game: [
    (Round(game)  # 1
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(WEAKEST)
                 .setCount(20)
                 .setSpacing(100)
        )
    ),
    (Round(game)  # 2
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(WEAKEST)
                 .setCount(35)
                 .setSpacing(75)
        )
    ),
    (Round(game)  # 3
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(WEAKEST)
                 .setCount(10)
                 .setSpacing(80)
        )
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(BLUE_EQ)
                 .setCount(5)
                 .setSpacing(80)
                 .setWaitsForLastRoundToBeFullySent()
        )
        .addWave(lambda key_: Wave(game, key_)
                 .setCount(15)
                 .setSpacing(80)
                 .setWaitsForLastRoundToBeFullySent()
        )
    ),
    (Round(game)  # 4
        .addWave(lambda key_: Wave(game, key_)
                 .setCount(10)
                 .setSpacing(60)
        )
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(BLUE_EQ)
                 .setCount(18)
                 .setSpacing(15)
                 .setWaitsForLastRoundToBeFullySent()
        )
        .addWave(lambda key_: Wave(game, key_)
                 .setCount(15)
                 .setSpacing(15)
                 .setWaitsForLastRoundToBeFullySent()
        )
    ),
    (Round(game)  # 5
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(BLUE_EQ)
                 .setCount(27)
                 .setSpacing(50)
        )
        .addWave(lambda key_: Wave(game, key_)
                 .setEnemyType(WEAKEST)
                 .setCount(5)
                 .setSpacing(150)
                 .setStartDelay(500)
        )
    )
]
