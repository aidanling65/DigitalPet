package com.example.tamagotchi.minigame

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import com.example.tamagotchi.main.data.model.TamagotchiState
import kotlinx.coroutines.delay

@Composable
fun PlatformerGameCanvas(tamagotchiState: TamagotchiState, modifier: Modifier){
    val playerX by remember { mutableStateOf(100f) }
    val playerY by remember { mutableStateOf(500f) }

    LaunchedEffect(Unit){
        while(true){
            delay(16L)
        }
    }

    Canvas(modifier = modifier){
        drawRect(color = Color.Blue, topLeft = Offset(playerX, playerY), size = Size(50f, 50f))
    }

}