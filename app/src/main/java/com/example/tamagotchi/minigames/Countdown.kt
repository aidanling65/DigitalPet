package com.example.tamagotchi.minigames

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
fun Countdown(isGameStarted: Boolean, countdown: Int) {
    if (!isGameStarted) {
        Text(
            "$countdown",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Black
        )
    }
}