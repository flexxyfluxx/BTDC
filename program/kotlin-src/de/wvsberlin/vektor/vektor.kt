package de.wvsberlin.vektor

import kotlin.math.pow
import kotlin.math.sqrt

fun abs(x: Vektor): Double = sqrt(x.x1.pow(2) + x.x2.pow(2))

fun dist(a: Vektor, b: Vektor) = abs(a - b)

class Vektor(val x1: Double, val x2: Double) {
    /**
     * 2d vectors wowowowowowo
     */

    override fun hashCode(): Int {
        var result = x1.hashCode()
        result = 31 * result + x2.hashCode()
        return result
    }
    override operator fun equals(other: Any?): Boolean {
        if (other !is Vektor) {
            return false
        }
        return this.hashCode() == other.hashCode()
    }

    /**
     * Addiere Vektor mit Vektor -> Vektor
     */
    operator fun plus(other: Vektor) = Vektor(this.x1 + other.x1, this.x2 + other.x2)
    operator fun minus(other: Vektor) = Vektor(this.x1 - other.x1, this.x2 - other.x2)

    operator fun times(other: Number): Vektor {
        /**
         * Multipliziere Vektor mit Skalar -> Vektor
         */
        val doubleOther = other as Double
        return Vektor(x1 * doubleOther, x2 * doubleOther)
    }

    /**
     * Multipliziere Vektor mit Vektor -> Skalarprodukt
     */
    operator fun times(other: Vektor): Number = this.x1 * other.x1 + this.x2 * other.x2

    operator fun div(scalar: Number): Vektor {
        /**
         * Teile Vektor durch Skalar → Vektor
         */
        val doubleScalar = scalar as Double
        return Vektor(x1 / doubleScalar, x2 / doubleScalar)
    }

    operator fun rangeTo(other: Vektor) = dist(this, other)

    val normal: Vektor
        /**
         * Normalenvektor zum geg. Vektor mit gleicher Länge
         */
        get() = Vektor(-x2, x1)

    val hnormal: Vektor
        /**
         * Hessischer Normalenvektor
         */
        get() = normal / abs(normal)
}

class Gerade(val p: Vektor, val v: Vektor) {
    /**
     * 'ne Vektorgerade der Form p-> + r * v->
     */
    operator fun invoke(r: Number) = p + v * (r as Double)
}
