package com.example.tamagotchi.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

enum class AppTheme(val theme: ColorScheme) {
    PURPLE(darkColorScheme(
        background = Purple700,
        primary = White,
        secondary = Color(0xFF6381EF),
        tertiary = Pink80,
        inverseOnSurface = Color.Black,
        surface = Color(0xDD442FB1),
        onSurface = Color(0xFFAA9864),
        surfaceContainer = Color(0xFF6381EF),
    )),
    PINK( lightColorScheme(
        background = Color(0XFFFBB5FF),
        primary = Black,
        secondary = Color(0xFFEE90FD),
        tertiary = Pink40,
        inverseOnSurface = Color.White,
        onBackground = Color(0xFFAFB6F1),
        surface = Color(0xA4EE90FD),
        onSurface = Color(0xFFF5DB86),
        surfaceContainer= Color(0xFF9FA1F1)
    )),
    GREEN(lightColorScheme(
        background = Color(0xFF81EF65),
        primary = Black,
        secondary = Color(0xFFB9FFA5),
        tertiary = Color(0xA455BF55),
        inverseOnSurface = White,
        surface = Color(0x9058A553),
        onSurface = Color(0xFFF5DB86),
        surfaceContainer = Color(0xFF74C162),
    )),
    RED(darkColorScheme(
        background = Color(0xFFFF6C6C),
        primary = White,
        secondary = Color(0xFFFFA2A2),
        tertiary = Pink80,
        inverseOnSurface = Color.Black,
        surface = Color(0xDDE37070),
        onSurface = Color(0xFFCEB97D),
        surfaceContainer = Color(0xFFFFA2A2),
    )),
    YELLOW(lightColorScheme(
        background = Color(0xFFFFF3BC),
        primary = Black,
        secondary = Color(0xFFFFDE84),
        tertiary = Pink80,
        inverseOnSurface = Color.Black,
        surface = Color(0xDDFDD58D),
        onSurface = Color(0xFFF6DA8A),
        surfaceContainer = Color(0xFFFDDC6C),
    )),
    ORANGE(lightColorScheme(
        background = Color(0xFFFFB576),
        primary = Black,
        secondary = Color(0xFFFF8346),
        tertiary = Pink80,
        inverseOnSurface = Color.Black,
        surface = Color(0xFFEF9B63),
        onSurface = Color(0xFFF5DB86),
        surfaceContainer = Color(0xFFEF9B63),
    ));

    companion object{
        fun fromOrdinal(ordinal: Int) = entries[ordinal]
    }
}