package com.example.tamagotchi.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.PetState
import com.example.tamagotchi.main.ui.components.PetDisplay

enum class PetColor(val color: Color) {
    LCD(
        Color(0xFF869484)
    ),
    WHITE(
        Color.White
    ),
    GREY(
        Color.Gray
    ),
    RED(
        Color(0xFFFF6053)
    ),
    GREEN(
        Color(0xFF68DE6D)
    ),
    BLUE(
        Color(0xFF03A9F4)
    ),
    DARK_BLUE(
        Color(0xFF5858FF)
    ),
    PINK(
        Color(0xFFFA8BB2)
    ),
    PURPLE(
        Color(0xFFC77DC7)
    ),
    ORANGE(
        Color(0xFFFFBE5D)
    ),
    YELLOW(
        Color(0xFFFFEF6F)
    ),
    BROWN(
        Color(0xFF795548)
    );
}


@Preview
@Composable
fun ColorPreview() {
    PetDisplay(
        PetState(
            paused = false,
            ageStage = AgeStage.CHILD,
            animations = EvolutionAnimations.CHILD,
            loading = false,
            poop = true,
            color = PetColor.GREEN
        ),
        showEatingAnimation = 1
    )
}