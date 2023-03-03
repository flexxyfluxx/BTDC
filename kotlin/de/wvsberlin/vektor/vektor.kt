package de.wvsberlin.vektor

import kotlin.math.pow
import kotlin.math.sqrt
import kotlin.math.round
import kotlin.math.abs
import ch.aplu.jgamegrid.Location
import java.awt.Point

fun abs(x: Vektor): Double = sqrt(x.x.pow(2) + x.y.pow(2))

fun abs2(x: Vektor): Double = abs(x.x.pow(2) + x.y.pow(2))
// nützlich, falls nur ermittelt werden soll, welcher Vektor länger/kürzer ist

/**
 * dist:
 * auf 2 (Orts-)Vektoren angewandt: Abstand zw. den entsprechenden Punkten
 * auf Gerade und (Orts-)Vektor angewandt: Abstand des Punktes (als Ortsvektor) zur Gerade
 */
fun dist(a: Vektor, b: Vektor): Double = abs(a - b)
fun dist(g: Gerade, p: Vektor): Double = (p - g.p) * unitNormal(g.v)

fun unitNormal(vek: Vektor): Vektor {
    if(vek == Vektor.NullVektor) return Vektor.NullVektor  // Es geschieht sonst eine Division durch Null, die nict sehr knorke ist.
    val normal = Vektor(vek.y, -vek.x)
    return normal / abs(normal)
}

fun clampedDist(a: Vektor, b: Vektor, p: Vektor): Double {
    /**
     * clampedDist:
     * Brechnet den Abstand eines Punktes zu einer Strecke AB (aus geg. A und B):
     * Falls der Fußpkt auf AB liegt, gebe dies zurück.
     * Sonst, gebe die Entfernung zum nächsten Pkt A oder B zurück.
     */

    // herausfinden, ob Fußpkt zwischen A und B (oder nicht)
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


open class Vektor(x: Number, y: Number) {
    /**
     * 2d vectors wowowowowowo
     * Vektoren im mathematischen Sinne.
     */

    open val x: Double
    open val y: Double

    init {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    companion object {
        @JvmField
        val NullVektor = Vektor(0,0)
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
    fun __add__(other: Vektor) = plus(other)

    operator fun minus(other: Vektor) = Vektor(this.x - other.x, this.y - other.y)
    fun __sub__(other: Vektor) = minus(other)

    /**
     * Multipliziere Vektor mit Skalar -> Vektor
     */
    operator fun times(other: Number): Vektor {
        val other_ = other.toDouble()
        return Vektor(x * other_, y * other_)
    }

    /**
     * Multipliziere Vektor mit Vektor -> Skalarprodukt
     */
    operator fun times(other: Vektor): Double = this.x * other.x + this.y * other.y
    // kein timesAssign mit Vektor, da sich dann der Objekttyp ändert

    fun __mul__(other: Number) = times(other)
    fun __rmul__(other: Number) = times(other)
    fun __mul__(other: Vektor) = times(other)

    /**
     * Teile Vektor durch Skalar → Vektor
     */
    operator fun div(scalar: Number): Vektor {
        val scalar_ = scalar.toDouble()
        return Vektor(x * scalar_, y * scalar_)
    }
    fun __div__(other: Number) = div(other)

    fun __abs__() = abs(this)

    fun __repr__() = "de.wvsberlin.vektor.Vektor[${x}, ${y}]@${hashCode()}"

    fun __str__() = "Vektor(${x}, ${y})"

    /**
     * Bei Verwendung von Kotlin kann man den Abstand zweier Vektoren als `v1..v2` schreiben.
     */
    operator fun rangeTo(other: Vektor) = dist(this, other)

    fun toLocation() = Location(round(x).toInt(), round(y).toInt())
    fun toPoint() = Point(round(x).toInt(), round(y).toInt())
}


class MutableVektor(x: Number, y: Number) : Vektor(x, y) {
    override var x: Double = x.toDouble()
    override var y: Double = y.toDouble()

    operator fun plusAssign(other: Vektor) {
        this.x += other.x
        this.y += other.y
    }
    fun __iadd__(other: Vektor) = plusAssign(other)

    operator fun minusAssign(other: Vektor) {
        this.x -= other.x
        this.y -= other.y
    }
    fun __isub__(other: Vektor) = minusAssign(other)

    operator fun timesAssign(other: Number) {
        val other_ = other.toDouble()
        this.x *= other_
        this.y *= other_
    }
    fun __imul__(other: Number) = times(other)

    operator fun divAssign(scalar: Number) {
        val scalar_ = scalar.toDouble()
        this.x /= scalar_
        this.y /= scalar_
    }
    fun __idiv__(other: Number) = divAssign(other)
}


class Gerade(val p: Vektor, val v: Vektor) {
    /**
     * 'ne Vektorgerade der Form p> + r * v>
     */
    operator fun invoke(r: Number) = p + v * (r.toDouble())
    fun __call__(r: Number) = invoke(r)
}
