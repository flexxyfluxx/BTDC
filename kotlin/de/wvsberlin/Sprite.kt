package de.wvsberlin

import ch.aplu.jgamegrid.GGBitmap

abstract class Sprite {
    companion object {
        @JvmStatic
        val ARROW = GGBitmap.getImage("../assets/sprites/arrows.png")!!
        @JvmStatic
        val BALLOON_WEAKEST = GGBitmap.getScaledImage("../assets/sprites/ballon1.png", 0.8, 0.0)!!
        @JvmStatic
        val BALLOON_BLUE = GGBitmap.getScaledImage("../assets/sprites/ballon2.png", 0.8, 0.0)!!
        @JvmStatic
        val CR_TOWER = GGBitmap.getImage("../assets/sprites/crTower.png")!!
        @JvmStatic
        val DENIED = GGBitmap.getImage("../assets/sprites/denied.png")!!
        @JvmStatic
        val EGIRL = GGBitmap.getImage("../assets/sprites/egirlsheesh.png")!!
        @JvmStatic
        val GOOSE = GGBitmap.getImage("../assets/sprites/gooseCondition.png")!!
        @JvmStatic
        val PICASSO = GGBitmap.getImage("../assets/sprites/picasso.png")!!
        @JvmStatic
        val SQUARE_PINK = GGBitmap.getImage("../assets/sprites/sprite.png")!!
        @JvmStatic
        val SQUARE_BLUE = GGBitmap.getImage("../assets/sprites/sprite2.png")!!
        @JvmStatic
        val SQUARE_GREEN = GGBitmap.getImage("../assets/sprites/spriteDebug.png")!!
        @JvmStatic
        val SPRITE = GGBitmap.getScaledImage("../assets/sprites/spritesprite.png", 0.8, 0.0)!!
        @JvmStatic
        val UWU = GGBitmap.getImage("../assets/sprites/uwu.png")!!
    }
    object Map {
        @JvmStatic
        val R208 = GGBitmap.getImage("../assets/maps/raum208v2.png")
        @JvmStatic
        val R208_LEGACY = GGBitmap.getImage("../assets/maps/raum208v1.png")
        @JvmStatic
        val JUNGLE = GGBitmap.getImage("../assets/maps/jungle.png")
    }
}
