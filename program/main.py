# -*- coding: utf-8 -*-
"""
Programmstartpunkt.
Diese Datei wird ausgeführt, wenn man das Start-Script ausführt.
"""

from __future__ import print_function  # mehr knorke als Print Keywords
from os.path import abspath
import sys

# nötige JARs für Jython zugänglich machen
sys.path.append(abspath("../lib/kotlin-stdlib-1.6.21.jar"))         # idk inwiefern nur eine Kotlin-stdlib-JAR nötig ist
sys.path.append(abspath("../lib/kotlin-stdlib-common-1.6.21.jar"))  # beide ig; kann ja nicht schaden
# sys.path.append(abspath("../lib/kotlinx-coroutines-core-jvm-1.6.4.jar"))  # möglicherweise nicht nötig; idk yet

import de.wvsberlin.factory as fac
from ch.aplu import jgamegrid as gg

import maputil as mu
import de.wvsberlin.vektor as vek


def main():
    myGrid = gg.GameGrid(800,800, 1, False)

    myMap = mu.loadMapFromJSON("./maps/test.json")


    myMap.setGridBg(myGrid, debug=True)


    myGrid.show()


if __name__ == "__main__":
    main()
