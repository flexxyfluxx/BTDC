package de.wvsberlin

import ch.aplu.jgamegrid.Actor
import ch.aplu.jgamegrid.GGBitmap
import de.wvsberlin.vektor.Vektor
import java.awt.image.BufferedImage

class Tower {
}

class HeldTower(val towerID: Int) : Actor(sprites[towerID]) {
    val pos = Vektor(480, 270)
    init {
        show()
    }
    companion object {
        @JvmStatic
        // we should move away from towerIDs to figure out which sprite should be shown..
        val sprites: Array<BufferedImage> = arrayOf(
            GGBitmap.getImage("sprites/crTower.png"),
            GGBitmap.getImage("sprites/egirlsheesh.png"),
            GGBitmap.getImage("sprites/picasso.png"),
            GGBitmap.getImage("sprites/spriteDebug.png"),
            GGBitmap.getImage("sprites/denied.png")
        )
    }
}
