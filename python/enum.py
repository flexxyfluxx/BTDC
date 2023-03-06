# -*- coding: utf-8 -*-

"""
enums. literally enums.
"""


def enum(**enums):
    return type('Enum', (), enums)
