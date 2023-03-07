# -*- coding: utf-8 -*-

"""
enums. literally enums.
"""


def makeEnum(**enums):
    return type('Enum', (), enums)
