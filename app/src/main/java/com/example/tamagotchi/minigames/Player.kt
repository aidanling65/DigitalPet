package com.example.tamagotchi.minigames

import androidx.compose.ui.graphics.ImageBitmap

class Player(var x: Float, var y: Float, var width: Float, var height: Float ) {
    val right: Float
        get() = x + width
    val bottom: Float
        get() = y + height
}