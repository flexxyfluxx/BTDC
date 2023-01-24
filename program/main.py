# -*- coding: utf-8 -*-
"""
Programmstartpunkt.
Diese Datei wird ausgeführt, wenn man das Start-Script ausführt.
"""

from os.path import abspath
import sys

# nötige JARs für Jython zugänglich machen
sys.path.append(abspath("../lib/kotlin-stdlib-1.6.21.jar"))         # idk inwiefern nur eine Kotlin-stdlib-JAR nötig ist
sys.path.append(abspath("../lib/kotlin-stdlib-common-1.6.21.jar"))  # beide ig; kann ja nicht schaden
sys.path.append(abspath("../lib/JGameGrid.jar"))
# sys.path.append(abspath("../lib/kotlinx-coroutines-core-jvm-1.6.4.jar"))  # möglicherweise nicht nötig; idk yet

import de.wvsberlin.factory as fac


def main():
    myFactory = fac.PyObjFactory(fac.interfaces.HelloWorldSayerType, "test", "HelloWorldSayer")

    myHelloWorldSayer = myFactory.make()

    myHelloWorldSayer.sayHelloWorld()


if __name__ == "__main__":
    main()
