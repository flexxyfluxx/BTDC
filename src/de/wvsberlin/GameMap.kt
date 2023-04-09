package de.wvsberlin

import ch.aplu.jgamegrid.GameGrid
import de.wvsberlin.vektor.Vektor
import java.awt.Color
import java.awt.Point
import java.awt.image.BufferedImage

/**
 * Dataclass für eine Map im Spiel.
 *
 * Hat ein (oder kein) Hintergrundbild und einen Pfad, der sich aus mehreren Knotenpunkten zusammensetzt.
 *
 * Enthält mehrere vordefinierte Maps.
 */
class GameMap {
    var bgImg: BufferedImage? = null  // allow null incase there just is no img
    var pathNodes = mutableListOf<Vektor>( /* this bitch empty. yeet? */ )
    var pathWidth: Double = 24.0

    companion object {
        @JvmStatic
        val RAUM208_V2 = GameMap()
                .setBgImg(Sprite.Map.R208)
                .addNode(Vektor( 862, 540 ))
                .addNode(Vektor( 862, 455 ))
                .addNode(Vektor( 655, 455 ))
                .addNode(Vektor( 655, 233 ))
                .addNode(Vektor( 610, 233 ))
                .addNode(Vektor( 610, 455 ))
                .addNode(Vektor( 502, 455 ))
                .addNode(Vektor( 502, 233 ))
                .addNode(Vektor( 455, 233 ))
                .addNode(Vektor( 455, 455 ))
                .addNode(Vektor( 335, 455 ))
                .addNode(Vektor( 335, 233 ))
                .addNode(Vektor( 287, 233 ))
                .addNode(Vektor( 287, 455 ))
                .addNode(Vektor( 135, 455 ))
                .addNode(Vektor( 135, 103 ))
                .addNode(Vektor( 960, 103 ))

        @JvmStatic
        val RAUM208_LEGACY = GameMap()
                .setBgImg(Sprite.Map.R208_LEGACY)
                .addNode(Vektor( 840,539 ))
                .addNode(Vektor( 840,453 ))
                .addNode(Vektor( 577,453 ))
                .addNode(Vektor( 577,216 ))
                .addNode(Vektor( 532,216 ))
                .addNode(Vektor( 532,453 ))
                .addNode(Vektor( 444,453 ))
                .addNode(Vektor( 444,220 ))
                .addNode(Vektor( 400,220 ))
                .addNode(Vektor( 400,453 ))
                .addNode(Vektor( 295,453 ))
                .addNode(Vektor( 295,221 ))
                .addNode(Vektor( 250,221 ))
                .addNode(Vektor( 250,453 ))
                .addNode(Vektor( 155,453 ))
                .addNode(Vektor( 155,135 ))
                .addNode(Vektor( 959,135 ))

        @JvmStatic
        val JUNGLE = GameMap()
                .setBgImg(Sprite.Map.JUNGLE)
                .addNode(Vektor( 351, 520 ))
                .addNode(Vektor( 406, 483 ))
                .addNode(Vektor( 201, 407 ))
                .addNode(Vektor( 109, 321 ))
                .addNode(Vektor( 89 , 209 ))
                .addNode(Vektor( 28 , 142 ))
                .addNode(Vektor( 114, 112 ))
                .addNode(Vektor( 125, 63  ))
                .addNode(Vektor( 376, 86  ))
                .addNode(Vektor( 565, 36  ))
                .addNode(Vektor( 545, 113 ))
                .addNode(Vektor( 668, 111 ))
                .addNode(Vektor( 674, 192 ))
                .addNode(Vektor( 793, 245 ))
                .addNode(Vektor( 795, 290 ))

        @JvmStatic
        val theMaps = arrayOf(
                RAUM208_V2,
                RAUM208_LEGACY,
                JUNGLE
        )
    }

    /**
     * Bei der Erstellung der Map zu verwenden.
     *
     * Füge weitere Node hinzu.
     */
    fun addNode(node: Vektor): GameMap {
        pathNodes.add(node)
        return this
    }

    /**
     * Bei der Erstellung der Map zu verwenden.
     *
     * Gebe der Map ein Hintergrundbild.
     */
    fun setBgImg(img: BufferedImage): GameMap {
        bgImg = img
        return this
    }

    /**
     * Bei der Erstellung der Map zu verwenden.
     *
     * Setze Pfadbreite, d.h. wie weit ein Turm vom Pfad platziert sein muss.
     */
    fun setPathWidth(width: Number): GameMap {
        pathWidth = width.toDouble()
        return this
    }

    /**
     * Übertrage das Hintergrundbild der Map auf ein GameGrid und zeichne optional den Pfad der Map in rot ein.
     */
    fun setBgOfGrid(grid: GameGrid, debug: Boolean = false) {
        // this is a reason why Kotlin is lovely.
        // Setters/getters are automatically converted to something that looks like a regular fucking field,
        // because normal ppl (aka. not Java devs) would rather directly access a field, rather than have to go through
        // some setter/getter bullshit.
        // fr, this is so much more concise and readable???
        val bg = grid.bg

        // if we have a bgImg, push it to the gamegrid.
        // clear first, to make sure we are truly showing what *we* want.
        bg.clear()
        if (bgImg != null) bg.drawImage(bgImg)

        if (!debug)
            return

        // If debug flag is set, also draw debug line where the path is.
        bg.paintColor = Color.RED
        bg.lineWidth = 3

        lateinit var point0: Point
        lateinit var point1: Point

        for (c in 0 until pathNodes.lastIndex) {
            point0 = pathNodes[c].toPoint()
            point1 = pathNodes[c+1].toPoint()
            bg.drawLine(point0, point1)
        }
    }
}