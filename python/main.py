# -*- coding: utf-8 -*-
"""
Programmstartpunkt.
Diese Datei wird ausgeführt, wenn man das Start-Script ausführt.
"""

from __future__ import print_function  # mehr knorke als Print Keywords
import syspaths  # RULE 1: DON'T REMOVE THIS SPECIFIC USELESS IMPORT (IT IS NOT USELESS)
from menu import Menu


def main():
    mainMenu = Menu()
    mainMenu.setVisible(True)


if __name__ == "__main__":
    main()