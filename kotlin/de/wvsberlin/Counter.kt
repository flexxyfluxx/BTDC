package de.wvsberlin

class Counter {
    private var c = -1
    fun iterator(): Int {
        c += 1
        return c
    }

    // jython compatibility
    fun next(): Int {
        return iterator()
    }
    fun __iter__(): Counter {
        return this
    }
}