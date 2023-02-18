package de.wvsberlin.vektor

import kotlin.math.pow
import kotlin.math.sqrt


fun abs(x: Vektor): Double = sqrt(x.y.pow(2) + x.y.pow(2))

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

class Vektor(var x: Double, var y: Double) {
    /**
     * 2d vectors wowowowowowo
     * Vektoren im mathematischen Sinne.
     */

    /**
     * Normalvektor normalisiert
     */
    private lateinit var _normal: Vektor
    // private, weil lateinit var; sollte aber eig nicht veränderlich sein

    val normal
        get() = _normal
        // Der unnormalisierte Normalvektor müsste dieselbe Länge wie der "eigentliche" Vektor haben.

    init {
        _normal = Vektor(y,-x) / abs(this)
    }

    override fun hashCode(): Int = 31 * x.hashCode() + y.hashCode()

    override operator fun equals(other: Any?): Boolean {
        if (other !is Vektor) {
            return false
        }
        return this.hashCode() == other.hashCode()
    }

    /**
     * Addiere/Subtrahiere Vektor mit Vektor -> Vektor
     */
    operator fun plus(other: Vektor) = Vektor(this.x + other.x, this.y + other.y)
    operator fun plusAssign(other: Vektor) {
        this.x += other.x
        this.y += other.y
    }
    operator fun minus(other: Vektor) = Vektor(this.x - other.x, this.y - other.y)
    operator fun minusAssign(other: Vektor) {
        this.x -= other.x
        this.y -= other.y
    }

    /**
     * Multipliziere Vektor mit Skalar -> Vektor
     */
    operator fun times(other: Double): Vektor = Vektor(x * other , y * other)
    operator fun times(other: Int): Vektor = this.times(other.toDouble())
    operator fun times(other: Float): Vektor = this.times(other.toDouble())
    operator fun timesAssign(other: Double) {
        this.x *= other
        this.y *= other
    }
    operator fun timesAssign(other: Float) = this.timesAssign(other.toDouble())
    operator fun timesAssign(other: Int) = this.timesAssign(other.toDouble())

    /**
     * Multipliziere Vektor mit Vektor -> Skalarprodukt
     */
    operator fun times(other: Vektor): Double = this.x * other.x + this.y * other.y
    // kein timesAssign mit Vektor, da sich dann der Objekttyp ändert

    /**
     * Teile Vektor durch Skalar → Vektor
     */
    operator fun div(scalar: Double): Vektor = Vektor(x * scalar, y * scalar)
    operator fun div(scalar: Int): Vektor = this.div(scalar.toDouble())
    operator fun div(scalar: Float): Vektor = this.div(scalar.toDouble())
    operator fun divAssign(scalar: Double) {
        this.x /= scalar
        this.y /= scalar
    }
    operator fun divAssign(scalar: Float) = this.divAssign(scalar.toDouble())
    operator fun divAssign(scalar: Int) = this.divAssign(scalar.toDouble())


    /**
     * Bei Verwendung von Kotlin kann man den Abstand zweier Vektoren als `v1..v2` schreiben.
     */
    operator fun rangeTo(other: Vektor) = dist(this, other)
}


class Gerade(val p: Vektor, val v: Vektor) {
    /**
     * 'ne Vektorgerade der Form p> + r * v>
     */
    operator fun invoke(r: Number) = p + v * (r as Double)
}
