package com.example.tamagotchi.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun DigitalPetTheme(
    appTheme: AppTheme = AppTheme.PURPLE,
    content: @Composable () -> Unit
) {
    val colorScheme = appTheme.theme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}