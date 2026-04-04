package com.example.digitalpet.minigames

import android.util.Log

data class Obstacle(
    var x: Float,
    var y: Float,
    var width: Float,
    var height: Float,
    var xVelocity: Float = 0f,
    var yVelocity: Float = 0f,
    var xAcceleration: Float = 0f,
    var yAcceleration: Float = 0f,
    val xTerminalVelocity: Float = Float.MAX_VALUE,
    val yTerminalVelocity: Float = Float.MAX_VALUE,
    var maxY: Float = Float.MAX_VALUE
) {
    val right: Float
        get() = x + width
    val bottom: Float
        get() = y + height

    fun isTouching(obstacle: Obstacle, lenience: Float = 0f): Boolean {
        val xTouch = obstacle.right > this.x && obstacle.x < this.right
        val yTouch = obstacle.bottom > this.y + lenience && obstacle.y < this.bottom - lenience

        return xTouch && yTouch
    }

    fun move() {
        x += xVelocity
        y = (y + yVelocity).coerceAtMost(maxY - height)
        xVelocity = (xVelocity + xAcceleration).coerceIn(-xTerminalVelocity, xTerminalVelocity)
        yVelocity = (yVelocity + yAcceleration).coerceIn(-yTerminalVelocity, yTerminalVelocity)
        Log.d("Obstacle", "moving: Coords: (${x}, ${y}), Velocity: (${xVelocity}, ${yVelocity})")
    }
}