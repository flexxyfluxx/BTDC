# -*- coding: utf-8 -*-
"""
Programmstartpunkt.
Diese Datei wird ausgeführt, wenn man das Start-Script ausführt.
"""

# Kotlin-Klassen importierbar machen
import sys  # auch bekannt als org.python.SystemState. wtaf
from os.path import abspath
sys.path.append(abspath("./kotlin/out/production/"))  # Dieser Dateipfad enthält die kompilierten Kotlin-Klassen.

import de.wvsberlin.factory as fac


def main():
    myFactory = fac.PyObjFactory(fac.interfaces.HelloWorldSayerType, "test", "HelloWorldSayer")

    myHelloWorldSayer = myFactory.make()

    myHelloWorldSayer.sayHelloWorld()


if __name__ == "__main__":
    main()
