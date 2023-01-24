# -*- coding: utf-8 -*-
"""
Programmstartpunkt.
Diese Datei wird ausgeführt, wenn man das Start-Script ausführt.
"""

from __future__ import print_function
from os.path import abspath
import sys

# nötige JARs für Jython zugänglich machen
sys.path.append(abspath("../lib/kotlin-stdlib-1.6.21.jar"))         # idk inwiefern nur eine Kotlin-stdlib-JAR nötig ist
sys.path.append(abspath("../lib/kotlin-stdlib-common-1.6.21.jar"))  # beide ig; kann ja nicht schaden
sys.path.append(abspath("../lib/jgamegrid"))
# sys.path.append(abspath("../lib/kotlinx-coroutines-core-jvm-1.6.4.jar"))  # möglicherweise nicht nötig; idk yet

import de.wvsberlin.factory as fac
import ch.aplu.jgamegrid as gg


class CountingActor(gg.Actor):
    def __init__(self):
        gg.Actor.__init__(self)
        self.c = 0

    def act(self):
        print("C at start:", self.c)
        self.c = 0
        for i in range(5000000):
            self.c+=1
        print("C in between", self.c)
        for i in range(5000000):
            self.c+=1
        print("C at end:", self.c)


def main():
    myGrid = gg.GameGrid(600,600)
    myActor = CountingActor()

    myGrid.addActor(myActor, gg.Location(0,0))

    myGrid.setSimulationPeriod(500)
    
    myGrid.show()
    myGrid.doRun()


if __name__ == "__main__":
    main()
