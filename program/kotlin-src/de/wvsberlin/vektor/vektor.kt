package de.wvsberlin.vektor

import kotlin.math.pow
import kotlin.math.sqrt

fun abs(x: Vektor): Double = sqrt(x.x1.pow(2) + x.x2.pow(2))

/**
 * dist:
 * auf 2 (Orts-)Vektoren angewandt: Abstand zw. den entsprechenden Punkten
 * auf Gerade und (Orts-)Vektor angewandt: Abstand des Punktes (als Ortsvektor) zur Gerade
 */
fun dist(a: Vektor, b: Vektor): Double = abs(a - b)
fun dist(g: Gerade, p: Vektor): Double = (p - g.p) * g.v.normal

fun clampedDist(a: Vektor, b: Vektor, p: Vektor): Double {
    /**
     * clampedDist:
     * Brechnet den Abstand eines Punktes zu einer Strecke AB (aus geg. A und B):
     * Falls der Fußpkt auf AB liegt, gebe dies zurück.
     * Sonst, gebe die Entfernung zum nächsten Pkt A oder B zurück.
     */

    // herausfinden, ob Fußpkt zwischen A und B
    val AB = b - a
    val absAB = abs(AB)
    val AP = p - a

    val distanceAF = AB * AP / absAB
    // Erklärung: https://www.rhetos.de/html/lex/skalarprodukt_anschaulich.htm
    // basically liefert das Skalarprodukt zweier Vektoren die Länge der Projektion des einen Vektors auf dem anderen
    // mal die Länge des anderen. Teilt man durch die Länge des anderen (hier AB), erhält man die Länge der Projektion.

    return when {
        distanceAF < 0     -> dist(a, p)
        distanceAF > absAB -> dist(b, p)
        else               -> dist(Gerade(a, AB), p)
    }
    /* wishful thinking
    return dist(when {
        distanceAF < 0     -> a
        distanceAF > absAB -> b
        else               -> Gerade(a, AB)
    }, p)  // fails, because Kotlin needs to know the exact type at compile time :<
           // dynamic scripting langs are more fun in this way.
     */
}

open class Vektor(open val x1: Double, open val x2: Double) {
    /**
     * 2d vectors wowowowowowo
     * Vektoren im mathematischen Sinne.
     */

    /**
     * Normalvektor normalisiert
     */
    val normal: Vektor

    init {
        // FIXME maybe this works??? idfk
        val tmpvec = Vektor(x2, -x1)
        normal = tmpvec / abs(tmpvec)
    }

    override fun hashCode(): Int = 31 * x1.hashCode() + x2.hashCode()

    override operator fun equals(other: Any?): Boolean {
        if (other !is Vektor) {
            return false
        }
        return this.hashCode() == other.hashCode()
    }

    /**
     * Addiere/Subtrahiere Vektor mit Vektor -> Vektor
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
    operator fun times(other: Vektor): Double = this.x1 * other.x1 + this.x2 * other.x2

    operator fun div(scalar: Number): Vektor {
        /**
         * Teile Vektor durch Skalar → Vektor
         */
        val doubleScalar = scalar as Double
        return Vektor(x1 / doubleScalar, x2 / doubleScalar)
    }

    operator fun rangeTo(other: Vektor) = dist(this, other)

}

val NullVektor = Vektor(0.0,0.0)

/**
 * mutable vector (it will prob be useful ww)
 * Vektor, aber die Attrs sind mutable
 */
class MutableVektor(override var x1: Double, override var x2: Double) : Vektor(x1, x2) {
    fun set(x1: Double, x2: Double) {
        /**
         * um alles auf einmal zu setzen
         */
        this.x1 = x1
        this.x2 = x2
    }
}

class Gerade(val p: Vektor, val v: Vektor) {
    /**
     * 'ne Vektorgerade der Form p> + r * v>
     */
    operator fun invoke(r: Number) = p + v * (r as Double)
}
