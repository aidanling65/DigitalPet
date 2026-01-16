package com.example.tamagotchi.minigames

class Obstacle(var x: Float, var y: Float, val width: Float, var height: Float) {
    val right: Float
        get() = x + width
    val bottom: Float
        get() = y + height

    fun isTouching(player: Player, lenience: Float = 0f): Boolean {
        val xTouch = player.right > this.x && player.x < this.right
        val yTouch = player.bottom > this.y + lenience && player.y < this.bottom - lenience

        return xTouch && yTouch
    }
}