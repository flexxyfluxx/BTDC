package de.wvsberlin

import ch.aplu.jgamegrid.GGBitmap
import ch.aplu.jgamegrid.GameGrid
import de.wvsberlin.vektor.Vektor
import java.awt.Color
import java.awt.Point
import java.awt.image.BufferedImage

class Map {
    var bgImg: BufferedImage? = null  // allow null incase there just is no img
    var pathNodes = MutableList(0) { Vektor.NullVektor }  // I think MutableList is the correct choice..?

    fun addNode(node: Vektor): Map {
        pathNodes.add(node)
        return this
    }

    fun setBgImg(img: BufferedImage): Map {
        bgImg = img
        return this
    }

    fun setBgOfGrid(grid: GameGrid, debug: Boolean = false) {
        // this is a reason why java is fucking retarded.
        // a java dev would do:
        //
        //      val bg = grid.getBg()
        //
        // and me too, because I didnt fukign know that I could just get the reference directly
        // why the fukc does such a method even exist.. I do not understand java devs' obsession w/ simple setters/getters.
        // just directly assign to the fukgign field ffs????
        // argh.
        val bg = grid.bg
        if (bgImg != null) bg.drawImage(bgImg)

        if (!debug) return

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
    fun setBgOfGrid(grid: GameGrid): Map {
        setBgOfGrid(grid, debug = false)
        return this
    }
}