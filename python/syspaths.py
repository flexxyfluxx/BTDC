# -*- coding: utf-8 -*-
"""
Die Hoffnung ist, dass man dieses Modul einfach in eine Datei importieren kann, um die Syspath einzurichten.
"""
from os.path import abspath
import sys

sys.path.append(abspath("../lib/kotlin-stdlib-1.8.10.jar"))  # idk inwiefern nur eine Kotlin-stdlib-JAR nötig ist
sys.path.append(abspath("../lib/kotlin-stdlib-common-1.8.10.jar"))  # beide ig; kann ja nicht schaden
sys.path.append(abspath("../lib/jgamegrid"))
sys.path.append(abspath("../out"))

# sys.path.append(abspath("../lib/kotlinx-coroutines-core-jvm-1.6.4.jar"))  # möglicherweise nicht nötig; idk yet
