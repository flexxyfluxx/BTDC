package de.wvsberlin.vektor

import ch.aplu.jgamegrid.Location
import java.awt.Point
import kotlin.math.*
import java.lang.Math.toDegrees
import java.lang.Math.toRadians

/**
 * 2d vectors wowowowowowo
 *
 * 2-dimensionale Vektoren im mathematischen Sinne.
 */
open class Vektor(x: Number, y: Number) {
    open val x: Double
    open val y: Double

    init {
        this.x = x.toDouble()
        this.y = y.toDouble()
    }

    companion object {  // Da wir keine Fields auf Package-Level haben können, weil Java sonst rumheult, müssen wir
                        // alle Funktionen, die ich sonst gern auf Package-Level hätte, im Companion Object definieren.
                        // Tun wir dies nicht, erstellt der Compiler eine weitere Klasse `VektorKt`, in der die Sachen,
                        // die auf Package-Level geschrieben werden, landen, damit Java damit klarkommt... wtf.
                        // Suboptimal, da ich keinen Bock habe, das erklären zu müssen.
                        // OOP ist ja toll und so, aber ich will Funktionen auf Package-Level :((( ffs
        /**
         * Der einzig wahre Nulvektor.
         *
         * Äquivalent zum mathematischen σ. (Bitte denk dir den Pfeil darüber selbst dazu.)
         */
        @JvmField
        val NullVektor = Vektor(0,0)

        @JvmStatic
        // um einen Vektor auch mit Richtung und Länge initialisieren zu können
        fun fromAngleAndMagnitude(angle: Double, magnitude: Number) = Vektor(cos(toRadians(angle)), sin(toRadians(angle))) * magnitude.toDouble()


        /**
         * Auf 2 (Orts-)Vektoren angewandt: Abstand zw. den entsprechenden Punkten
         *
         * Auf Gerade und (Orts-)Vektor angewandt: Abstand des Punktes (als Ortsvektor) zur Gerade.
         *
         * Mit Option für quadriert oder normal.
         */
        @JvmStatic
        fun dist(a: Vektor, b: Vektor, doSqrt: Boolean): Double = (a - b).abs(doSqrt)

        /**
         * Auf 2 (Orts-)Vektoren angewandt: Abstand zw. den entsprechenden Punkten
         *
         * Auf Gerade und (Orts-)Vektor angewandt: Abstand des Punktes (als Ortsvektor) zur Gerade.
         */
        @JvmStatic
        fun dist(a: Vektor, b: Vektor): Double = (a - b).abs(true)


        /**
         * Berechnet den Abstand eines Punktes zu einer Strecke AB (aus geg. A und B):
         *
         * Falls der Fußpkt auf AB liegt, gebe dies zurück.
         *
         * Sonst, gebe die Entfernung zum nächsten Pkt A oder B zurück.
         */
        @JvmStatic
        fun clampedDist(a: Vektor, b: Vektor, p: Vektor): Double {
            // keine Option doSqrt, da sonst in manchen Fällen noch
            // hoch 2 genommen werden müsste lul

            // herausfinden, ob Fußpkt zwischen A und B (oder nicht)
            val ABvek = b - a
            val absAB = ABvek.abs()
            val APvek = p - a

            val distanceAF = (ABvek * APvek) / absAB
            // Erklärung: https://www.rhetos.de/html/lex/skalarprodukt_anschaulich.htm
            // basically liefert das Skalarprodukt zweier Vektoren die Länge der Projektion des einen Vektors auf dem anderen
            // mal die Länge des anderen. Teilt man durch die Länge des anderen (hier AB), erhält man die Länge der Projektion.

            when {
                distanceAF < 0     -> return dist(a, p)
                distanceAF > absAB -> return dist(b, p)
                else               -> return Gerade.dist(Gerade(a, ABvek), p)
            }
        }
    }

    /**
     * Ermittle Einheitsvektor des Vektors.
     */
    fun getUnitized(): Vektor {
        if (this == NullVektor)
            throw IllegalStateException("Cannot unitize Nullvektor! (The laws of math forbid it)")
        return this / this.abs()  // Division durch Null ist nicht gut diese, darum hat ein Nullvektor keinen definierten Einheitsvektor.
    }

    /**
     * Ermittle einen hesse'schen Normalenvektor des Vektors.
     */
    fun getUnitNormal(): Vektor {  // Ich kann nicht garantieren, dass der Vektor in die "richtige" Richtung zeigt.
        if (this == NullVektor)
            throw IllegalStateException("Cannot get unit normal of NullVektor! (The laws of maths forbid it)")
        return Vektor(y, -x).getUnitized()
    }

    /**
     * Ermittle den Winkel des Vektors (in Grad).
     */
    fun getAngle(): Double {
        val unitized: Vektor
        if (abs() != 1.0)
            unitized = getUnitized()
        else
            unitized = this

        return (360 +
            if (unitized.x < 0) {
                180 + toDegrees(asin(-unitized.y))
            } else toDegrees(asin(unitized.y))
        ) % 360
    }

    /**
     * Betrag des Vektors: Ermittle wahlweise seine wahre oder quadrierte Länge.
     *
     * Die quadrierte Länge ist halt einfacher zu berechnen, da man sich dadurch die sqrt-Funktion spart.
     */
    fun abs(doSqrt: Boolean = true): Double {
        val unsqrted = x*x + y*y

        // Aus Optimierungsgründen darf man zwischen der wahren und der quadrierten Länge des Vektors wöhlen,
        // weil sqrt sehr langsam sein soll.
        // Ist zudem eine sehr einfache Optimierung, also...
        return if (doSqrt) {
            sqrt(unsqrted)
        } else {
            unsqrted
        }
    }

    /**
     * Betrag des Vektors: Ermittle seine Länge.
     */
    fun abs(): Double = abs(true)

    override fun hashCode(): Int = 31 * x.hashCode() + y.hashCode()

    override operator fun equals(other: Any?): Boolean {
        if (other !is Vektor) return false
        return hashCode() == other.hashCode()
    }

    /**
     * Addiere Vektor mit Vektor.
     */
    operator fun plus(other: Vektor) = Vektor(this.x + other.x, this.y + other.y)

    /**
     * Subtrahiere Vektor von Vektor.
     */
    operator fun minus(other: Vektor) = Vektor(this.x - other.x, this.y - other.y)

    /**
     * tipliziere Vektor mit Skalar.
     */
    operator fun times(other: Number): Vektor {
        val otherD = other.toDouble()
        return Vektor(x * otherD, y * otherD)
    }

    /**
     * Ermittle Skalarprodukt zweier Vektoren.
     */
    operator fun times(other: Vektor): Double = (this.x * other.x) + (this.y * other.y)
    // kein timesAssign mit Vektor, da sich dann der Objekttyp ändert

    /**
     * Teile Vektor durch Skalar.
     */
    operator fun div(scalar: Number): Vektor {
        val Dscalar = scalar.toDouble()
        return Vektor(x / Dscalar, y / Dscalar)
    }

    open fun __repr__() = "de.wvsberlin.vektor.Vektor[${x}, ${y}]@${hashCode()}"

    open fun __str__() = "Vektor(${x}, ${y})"

    /**
     * Implementierung von Kotlins rangeTo-Operator:
     *
     * Statt `Vektor.dist(v1, v2)` kann man `v1..v2` schreiben.
     */
    operator fun rangeTo(other: Vektor) = dist(this, other)

    /**
     * Wandle Vektor zu `ch.aplu.jgamegrid.Location` um.
     */
    fun toLocation() = Location(round(x).toInt(), round(y).toInt())

    /**
     * Wandle Vektor zu `java.awt.Point` um.
     */
    fun toPoint() = Point(round(x).toInt(), round(y).toInt())
}

/**
 * Vektoren, aber veränderlich.
 *
 * `v1 += v2` erstellt also zB. keinen weiteren Vektor `v3` und setzt `v1` gleich den,
 * sondern addiert `v2` direkt zum Objekt `v1`.
 */
class MutableVektor(x: Number, y: Number) : Vektor(x, y) {
    override var x: Double = x.toDouble()
    override var y: Double = y.toDouble()

    /**
     * Setze Richtung des Vektors anhand einer geg. Richtung in Grad.
     *
     * Behält Länge des Vektors bei.
     */
    fun setAngle(angle: Number) {
        val vekLength = abs()
        x = cos(toRadians(angle.toDouble())) * vekLength
        y = sin(toRadians(angle.toDouble())) * vekLength
    }

    /**
     * Setze Länge des Vektors auf den geg. Wert.
     *
     * Behält Richtung des Vektors bei.
     */
    fun setLength(length: Number) {
        val factor = length.toDouble() / abs()
        x *= factor
        y *= factor
    }

    companion object {
        /**
         * Erstelle einen neuen MutableVektor anhand von Richtung (in Grad) und Länge, anstatt von Achsenlängen.
         */
        @JvmStatic
        fun fromAngleAndMagnitude(angle: Number, magnitude: Number = 1) =
            MutableVektor(cos(toRadians(angle.toDouble())), sin(toRadians(angle.toDouble()))) * magnitude

        /**
         * Erstelle anhand eines (normalen) Vektors einen äquivalenten MutableVektor.
         */
        @JvmStatic
        fun fromImmutable(ivektor: Vektor) = MutableVektor(ivektor.x, ivektor.y)
    }

    operator fun plusAssign(other: Vektor) {
        this.x += other.x
        this.y += other.y
    }

    operator fun minusAssign(other: Vektor) {
        this.x -= other.x
        this.y -= other.y
    }

    operator fun timesAssign(other: Number) {
        val otherD = other.toDouble()
        this.x *= otherD
        this.y *= otherD
    }

    operator fun divAssign(scalar: Number) {
        val Dscalar = scalar.toDouble()
        this.x /= Dscalar
        this.y /= Dscalar
    }

    override fun __repr__() = "de.wvsberlin.vektor.MutableVektor[${x}, ${y}]@${hashCode()}"

    override fun __str__() = "MutableVektor(${x}, ${y})"
}

/**
 * 2d-Geraden in Parameterform im mathematischen Sinne.
 *
 * `Gerade(p, v)` entspricht `g: x = p + rv` (Vektorpfeile bitte dazudenken)
 */
class Gerade(val p: Vektor, val v: Vektor) {
    override fun hashCode(): Int {
        var result = p.hashCode()
        result = 31 * result + v.hashCode()
        return result
    }
    override operator fun equals(other: Any?): Boolean {
        if (other !is Gerade) return false
        return hashCode() == other.hashCode()
    }

    /**
     * In der Mathematik bekommt man Funktionswerte der Funktion f mit der Schreibweise f(x).
     *
     * Warum sollte das beim Programmieren anders sein?
     */
    operator fun invoke(r: Number) = p + v * (r.toDouble())

    companion object {
        /**
         * Ermittle Abstand eines Punktes zu einer Gerade.
         */
        @JvmStatic
        fun dist(a: Gerade, b: Vektor): Double {
            val APvek = b - a.p
            val hessian = a.v.getUnitNormal()
            val scalarproduct = APvek * hessian
            return abs(scalarproduct)
        }
        // Bei der Abstandmessung zu einer Gerade brauchen wir komischerweise keine Sqrt-Option,
        // da wir nicht überhaupt Sqrt rechnen müssen, d.h. wir können an der Stelle keine weitere Performance herausholen.
    }
}
