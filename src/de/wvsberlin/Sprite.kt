package de.wvsberlin

import ch.aplu.jgamegrid.GGBitmap

/**
 * Sprites. Images. All of them, here, in one convenient class.
 */
abstract class Sprite {
    companion object {        val ARROW = GGBitmap.getScaledImage("sprites/arrows.png", 0.1, 0.0)!!
        val BALLOON_WEAKEST = GGBitmap.getScaledImage("sprites/red.png", 0.8, 0.0)!!
        val BALLOON_BLUE = GGBitmap.getScaledImage("sprites/blue.png", 0.8, 0.0)!!
        val BALLOON_GREEN = GGBitmap.getScaledImage("sprites/green.png", 0.8, 0.0)
        val BALLOON_YELLOW = GGBitmap.getScaledImage("sprites/yellow.png", 0.8, 0.0)
        val BALLOON_PINK = GGBitmap.getScaledImage("sprites/pink.png", 0.8, 0.0)
        val BALLOON_PURPLE = GGBitmap.getScaledImage("sprites/purple.png", 0.8, 0.0)
        val BALLOON_BLACK = GGBitmap.getScaledImage("sprites/black.png", 0.8, 0.0)
        val BALLOON_WHITE = GGBitmap.getScaledImage("sprites/white.png", 0.8, 0.0)
        val BALLOON_ZEBRA = GGBitmap.getScaledImage("sprites/zebra.png", 0.8, 0.0)
        val BALLOON_RAINBOW = GGBitmap.getScaledImage("sprites/rainbow.png", 0.8, 0.0)
        val BALLOON_CERAMIC = GGBitmap.getScaledImage("sprites/ceramic.png", 0.8, 0.0)
        val CR_TOWER = GGBitmap.getImage("sprites/crTower.png")!!
        val DENIED = GGBitmap.getImage("sprites/denied.png")!!
        val EGIRL = GGBitmap.getImage("sprites/egirlsheesh.png")!!
        val GOOSE = GGBitmap.getImage("sprites/gooseCondition.png")!!
        val PICASSO = GGBitmap.getImage("sprites/picasso.png")!!
        val SQUARE_PINK = GGBitmap.getImage("sprites/sprite.png")!!
        val SQUARE_BLUE = GGBitmap.getImage("sprites/sprite2.png")!!
        val SQUARE_GREEN = GGBitmap.getImage("sprites/spriteDebug.png")!!
        val SPRITE = GGBitmap.getScaledImage("sprites/spritesprite.png", 0.1, 0.0)!!
        val UWU = GGBitmap.getScaledImage("sprites/uwu.png", 0.1, 0.0)!!
    }

    /**
     * Subset of sprites: Map images. Grouped together, for your coonvenience.
     */
    // no, I am not fixing the doc comment typo. It's too funny.
    object Map {        val R208 = GGBitmap.getImage("maps/raum208v2.png")
        val R208_LEGACY = GGBitmap.getImage("maps/raum208v1.png")
        val JUNGLE = GGBitmap.getImage("maps/jungle.png")
    }
}
