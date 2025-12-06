package com.example.tamagotchi.minigame

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import kotlinx.coroutines.delay

@Composable
fun PlatformerGameCanvas(tamagotchiState: TamagotchiState) {
    var playerX by remember { mutableStateOf(100f) }
    var playerY by remember { mutableStateOf(0f) }
    var playerYVelocity by remember { mutableStateOf(0f) }
    var gravity by remember { mutableStateOf(5f) }

    var laps = 0
    var currentFrame by remember { mutableStateOf(0) }
    val playerBitmap = ImageBitmap.imageResource(id = tamagotchiState.animations.idle[currentFrame])

    var canvasWidth by remember { mutableStateOf(500f) }
    var obstacleX by remember { mutableStateOf(500f) }
    var obstacleXVelocity by remember { mutableStateOf(10f) }

    var timer by remember { mutableStateOf(0L) }


    LaunchedEffect(Unit) {
        while (true) {
            playerY = (playerY + playerYVelocity).coerceAtMost(450f)
            playerYVelocity += gravity

            obstacleX -= obstacleXVelocity
            if (obstacleX < 0) {
                obstacleX = canvasWidth
            }
            laps += 1
            if(laps % 30 == 0){
                currentFrame = (currentFrame + 1) % tamagotchiState.animations.idle.size
                laps = 0
            }
            timer += 16L
            delay(16L)
        }
    }

    LaunchedEffect(Unit) {
        currentFrame = (currentFrame + 1) % tamagotchiState.animations.idle.size
        delay(500L)
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .clip(RoundedCornerShape(10))
                .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                .background(colorResource(R.color.lcd))
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                val canvasHeight = size.height
                canvasWidth = size.width
                val obstacleHeight = canvasHeight
                val obstacleY = 0f


                drawRect(
                    Color(0xFF000000),
                    topLeft = Offset(obstacleX, obstacleY),
                    size = Size(20f, obstacleHeight)
                )
                drawImage(
                    image = playerBitmap,
                    topLeft = Offset(playerX, playerY)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { playerYVelocity = -60f }
        ) {
            Text("Jump", style = MaterialTheme.typography.bodyMedium)
        }
        Text("Score: ${timer / 200}", style=MaterialTheme.typography.bodyMedium)
    }

}