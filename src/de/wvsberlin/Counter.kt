package de.wvsberlin

class Counter {
    private var c = -1
    fun iterator(): Int {
        c += 1
        return c
    }

    fun next(): Int {
        return iterator()
    }
}