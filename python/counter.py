class Counter:
    def __init__(self):
        self.c = -1

    def __iter__(self):
        return self

    def next(self):
        self.c += 1
        return self.c
