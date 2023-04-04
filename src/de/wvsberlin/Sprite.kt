package de.wvsberlin

import ch.aplu.jgamegrid.GGBitmap

/**
 * Sprites. Images. All of them, here, in one convenient class.
 */
abstract class Sprite {
    companion object {
        @JvmStatic
        val ARROW = GGBitmap.getScaledImage("sprites/arrows.png", 0.1, 0.0)!!

        @JvmStatic
        val BALLOON_WEAKEST = GGBitmap.getScaledImage("sprites/ballon1.png", 0.8, 0.0)!!

        @JvmStatic
        val BALLOON_BLUE = GGBitmap.getScaledImage("sprites/ballon2.png", 0.8, 0.0)!!

        @JvmStatic
        val CR_TOWER = GGBitmap.getImage("sprites/crTower.png")!!

        @JvmStatic
        val DENIED = GGBitmap.getImage("sprites/denied.png")!!

        @JvmStatic
        val EGIRL = GGBitmap.getImage("sprites/egirlsheesh.png")!!

        @JvmStatic
        val GOOSE = GGBitmap.getImage("sprites/gooseCondition.png")!!

        @JvmStatic
        val PICASSO = GGBitmap.getImage("sprites/picasso.png")!!

        @JvmStatic
        val SQUARE_PINK = GGBitmap.getImage("sprites/sprite.png")!!

        @JvmStatic
        val SQUARE_BLUE = GGBitmap.getImage("sprites/sprite2.png")!!

        @JvmStatic
        val SQUARE_GREEN = GGBitmap.getImage("sprites/spriteDebug.png")!!

        @JvmStatic
        val SPRITE = GGBitmap.getScaledImage("sprites/spritesprite.png", 0.1, 0.0)!!

        @JvmStatic
        val UWU = GGBitmap.getScaledImage("sprites/uwu.png", 0.1, 0.0)!!
    }

    /**
     * Subset of sprites: Map images. Grouped together, for your coonvenience.
     */
    // no, I am not fixing the doc comment typo. It's too funny.
    object Map {
        @JvmStatic
        val R208 = GGBitmap.getImage("maps/raum208v2.png")

        @JvmStatic
        val R208_LEGACY = GGBitmap.getImage("maps/raum208v1.png")

        @JvmStatic
        val JUNGLE = GGBitmap.getImage("maps/jungle.png")
    }
}
