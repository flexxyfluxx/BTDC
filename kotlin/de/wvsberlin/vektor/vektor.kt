package de.wvsberlin.vektor

import ch.aplu.jgamegrid.Location
import java.awt.Point
import kotlin.math.*
import java.lang.Math.toDegrees
import java.lang.Math.toRadians
import kotlin.random.Random


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

    companion object {  // Da wir keine Fields auf Package-Level haben können, weil Java sonst rumheult, müssen wir
                        // alle Funktionen, die ich sonst gern auf Package-Level hätte, im Companion Object definieren.
                        // Tun wir dies nicht, erstellt der Compiler eine weitere Klasse `VektorKt`, in der die Sachen,
                        // die auf Package-Level geschrieben werden, landen, damit Java damit klarkommt... wtf.
                        // Suboptimal, da ich keinen Bock habe, das erklären zu müssen.
                        // OOP ist ja toll und so, aber ich will Funktionen auf Package-Level :((( ffs
        @JvmField
        val NullVektor = Vektor(0,0)

        @JvmStatic
        // um einen Vektor auch mit Richtung und Länge initialisieren zu können
        fun fromAngleAndMagnitude(angle: Double, magnitude: Double) = Vektor(cos(toRadians(angle)), sin(toRadians(angle))) * magnitude


        /**
         * dist:
         * auf 2 (Orts-)Vektoren angewandt: Abstand zw. den entsprechenden Punkten
         * auf Gerade und (Orts-)Vektor angewandt: Abstand des Punktes (als Ortsvektor) zur Gerade
         */
        @JvmStatic
        fun dist(a: Vektor, b: Vektor, doSqrt: Boolean): Double = (a - b).abs(doSqrt)

        @JvmStatic
        fun dist(a: Vektor, b: Vektor): Double = (a - b).abs(true)


        @JvmStatic
        fun clampedDist(a: Vektor, b: Vektor, p: Vektor): Double {  // keine Option doSqrt, da sonst in manchen Fällen noch
            // hoch 2 genommen werden müsste lul
            /**
             * clampedDist:
             * Berechnet den Abstand eines Punktes zu einer Strecke AB (aus geg. A und B):
             * Falls der Fußpkt auf AB liegt, gebe dies zurück.
             * Sonst, gebe die Entfernung zum nächsten Pkt A oder B zurück.
             */

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

    fun getUnitized(): Vektor {
        if (this == NullVektor)
            throw IllegalStateException("Cannot unitize Nullvektor! (The laws of math forbid it)")
        return this / this.abs()  // Division durch Null ist nicht gut diese, darum hat ein Nullvektor keinen definierten Einheitsvektor.
    }

    fun getUnitNormal(): Vektor {  // Ich kann nicht garantieren, dass der Vektor in die "richtige" Richtung zeigt.
        if (this == NullVektor)
            throw IllegalStateException("Cannot get unit normal of NullVektor! (The laws of maths forbid it)")
        return Vektor(y, -x).getUnitized()
    }

    fun getAngle(): Double {
        val unitized = getUnitized()

        return (360 +
            if (unitized.x < 0) {
                toDegrees(asin(unitized.y))
            } else toDegrees(asin(unitized.y))
        ) % 360
    }


    fun abs(doSqrt: Boolean = true): Double {
        val unsqrted = x*x + y*y

        return if (doSqrt) {
            sqrt(unsqrted)
        } else {
            unsqrted
        }
    }

    fun abs(): Double = abs(true)

    override fun hashCode(): Int = 31 * x.hashCode() + y.hashCode()

    override operator fun equals(other: Any?): Boolean {
        if (other !is Vektor) return false
        return hashCode() == other.hashCode()
    }
    fun __eq__(other: Any?) = equals(other)

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
        val otherD = other.toDouble()
        return Vektor(x * otherD, y * otherD)
    }

    /**
     * Multipliziere Vektor mit Vektor -> Skalarprodukt
     */
    operator fun times(other: Vektor): Double = (this.x * other.x) + (this.y * other.y)
    // kein timesAssign mit Vektor, da sich dann der Objekttyp ändert

    fun __mul__(other: Number) = times(other)
    fun __rmul__(other: Number) = times(other)
    fun __mul__(other: Vektor) = times(other)

    /**
     * Teile Vektor durch Skalar → Vektor
     */
    operator fun div(scalar: Number): Vektor {
        val Dscalar = scalar.toDouble()
        return Vektor(x / Dscalar, y / Dscalar)
    }
    fun __div__(other: Number) = div(other)

    fun __abs__() = abs()

    open fun __repr__() = "de.wvsberlin.vektor.Vektor[${x}, ${y}]@${hashCode()}"

    open fun __str__() = "Vektor(${x}, ${y})"

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

    fun setAngle(angle: Number) {
        val vekLength = abs()
        x = cos(toRadians(angle.toDouble())) * vekLength
        y = sin(toRadians(angle.toDouble())) * vekLength
    }

    fun setLength(length: Number) {
        val factor = length.toDouble() / abs()
        x *= factor
        y *= factor
    }

    companion object {
        @JvmStatic
        fun fromAngleAndMagnitude(angle: Number, magnitude: Number = 1) =
            MutableVektor(cos(toRadians(angle.toDouble())), sin(toRadians(angle.toDouble()))) * magnitude

        @JvmStatic
        fun fromImmutable(ivektor: Vektor) = MutableVektor(ivektor.x, ivektor.y)
    }

    operator fun plusAssign(other: Vektor) {
        this.x += other.x
        this.y += other.y
    }
    fun __iadd__(other: Vektor): MutableVektor {
        plusAssign(other)
        return this
    }

    operator fun minusAssign(other: Vektor) {
        this.x -= other.x
        this.y -= other.y
    }
    fun __isub__(other: Vektor): MutableVektor {
        minusAssign(other)
        return this
    }

    operator fun timesAssign(other: Number) {
        val otherD = other.toDouble()
        this.x *= otherD
        this.y *= otherD
    }
    fun __imul__(other: Number): MutableVektor {
        times(other)
        return this
    }

    operator fun divAssign(scalar: Number) {
        val Dscalar = scalar.toDouble()
        this.x /= Dscalar
        this.y /= Dscalar
    }
    fun __idiv__(other: Number): MutableVektor{
        divAssign(other)
        return this
    }

    override fun __repr__() = "de.wvsberlin.vektor.MutableVektor[${x}, ${y}]@${hashCode()}"

    override fun __str__() = "MutableVektor(${x}, ${y})"
}


class Gerade(val p: Vektor, val v: Vektor) {
    /**
     * 'ne Vektorgerade der Form p> + r * v>
     */

    override fun hashCode(): Int {
        var result = p.hashCode()
        result = 31 * result + v.hashCode()
        return result
    }
    override operator fun equals(other: Any?): Boolean {
        if (other !is Gerade) return false
        return hashCode() == other.hashCode()
    }
    fun __eq__(other: Any?) = equals(other)

    operator fun invoke(r: Number) = p + v * (r.toDouble())
    fun __call__(r: Number) = invoke(r)

    companion object {
        @JvmStatic
        fun dist(a: Gerade, b: Vektor): Double {
            val APvek = b - a.p
            val hessian = a.v.getUnitNormal()
            val scalarproduct = APvek * hessian
            return abs(scalarproduct)
        }
        // Bei der Abstandmessung zu einer Gerade brauchen wir komischerweise keine Sqrt-Option,
        // da wir nicht überhaupt Sqrt rechnen müssen, d.h. wir können nicht an der Stelle weitere Performance rausholen.
    }

    fun __repr__() = "de.wvsberlin.vektor.Gerade[p=${p}, v=${v}]@${hashCode()}"
    fun __str__() = "Gerade(p=(${p.x}, ${p.y}), v=(${v.x}, ${v.y}))"
}
