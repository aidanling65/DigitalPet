package com.example.tamagotchi.minigames

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.utils.getAnimationFrames
import kotlinx.coroutines.delay

@Composable
fun JumpGameCanvas(tamagotchiState: TamagotchiState, gameViewModel: GameViewModel) {

    val context = LocalContext.current
    val playerAnimationFrames = remember(tamagotchiState.animations.play) {
        getAnimationFrames(context, tamagotchiState.animations.play)
    }


    var canvasHeight by remember { mutableFloatStateOf(1000f) }
    var canvasWidth by remember { mutableFloatStateOf(1000f) }

    var laps = 0L
    var currentFrame by remember { mutableIntStateOf(0) }
    if (playerAnimationFrames.isEmpty()) {
        Text("Error: Could not load player animation.")
        return
    }

    val playerBitmap = playerAnimationFrames[currentFrame]
    val playerRenderSize = IntSize(
        playerBitmap.width,
        playerBitmap.height
    )
    val playerRenderedHeight = playerRenderSize.height
    val playerRenderedWidth = playerRenderSize.width

    var playerX by remember { mutableFloatStateOf(100f) }
    var playerY by remember { mutableFloatStateOf(canvasHeight - playerRenderedHeight) }
    var playerYVelocity by remember { mutableFloatStateOf(0f) }
    val gravity = 4f
    var playerJumped by remember { mutableStateOf(false) }

    var obstacleHeight: Float
    val obstacleWidth = 30f
    var obstacleX by remember { mutableFloatStateOf(canvasWidth * 2) }
    var obstacleY by remember { mutableFloatStateOf(0f) }
    var obstacleXVelocity by remember { mutableFloatStateOf(10f) }

    var timer by remember { mutableLongStateOf(0L) }
    var isGameOver by remember { mutableStateOf(false) }
    var isGameOverScreen by remember { mutableStateOf(false) }
    var isGameStarted by remember { mutableStateOf(false) }
    val delay = 10L
    val frameRate = 500L / delay

    if (isGameStarted) {
        LaunchedEffect(Unit) {
            while (true) {
                if (isGameOver) {
                    gameViewModel.gameScore((timer / 200L).toInt())
                    return@LaunchedEffect
                }
                val maxY = canvasHeight - playerRenderedHeight
                playerY =
                    (playerY + playerYVelocity).coerceAtMost(maxY)
                if (playerY == maxY) {
                    playerJumped = false
                }
                playerYVelocity += gravity

                obstacleX -= obstacleXVelocity
                obstacleHeight = canvasHeight / 10
                if (obstacleX < 0) {
                    obstacleX = canvasWidth
                }
                laps += 1
                if (laps % frameRate == 0L) {
                    currentFrame = (currentFrame + 1) % playerAnimationFrames.size
                    laps = 0
                    obstacleXVelocity += 0.02f
                }

                val playerRight = playerX + playerRenderedWidth
                val playerBottom = playerY + playerRenderedHeight

                val collisionX = playerRight > obstacleX && playerX < obstacleX + obstacleWidth
                val collisionY = playerBottom > obstacleY

                if (collisionX && collisionY) {
                    Log.d(
                        "Collision",
                        "Player: (${playerX}, ${playerY})\nObstacle: (${obstacleX}, ${obstacleY}"
                    )
                    isGameOver = true
                    delay(500L)
                    isGameOverScreen = true
                }

                timer += 10L

                delay(delay)
            }
        }
    }

    var countdown by remember { mutableIntStateOf(3) }
    if (!isGameStarted) {
        obstacleX = canvasWidth
        playerY = canvasHeight - playerRenderedHeight
        LaunchedEffect(Unit) {
            while (countdown > 0) {
                delay(1000L)
                countdown -= 1
            }
            isGameStarted = true
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .padding(bottom = 8.dp)
        ) {
            if (isGameStarted && !isGameOver) {
                Text(
                    "Score: ${timer / 200}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.white),
                    textAlign = TextAlign.Start
                )
            } else {
                Text("", style = MaterialTheme.typography.bodyMedium)
            }
        }
        Box(
            Modifier
                .clip(RoundedCornerShape(10))
                .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                .background(colorResource(R.color.lcd))
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
                canvasHeight = size.height
                canvasWidth = size.width
                obstacleHeight = canvasHeight / 10
                obstacleY = canvasHeight - obstacleHeight

                if (!isGameOverScreen) {
                    if (isGameStarted) {
                        drawRect(
                            Color(0xFF000000),
                            topLeft = Offset(obstacleX, obstacleY),
                            size = Size(obstacleWidth, obstacleHeight)
                        )
                    }

                    drawImage(
                        image = playerBitmap,
                        srcOffset = IntOffset.Zero,
                        srcSize = IntSize(playerBitmap.width, playerBitmap.height),
                        dstOffset = IntOffset(playerX.toInt(), playerY.toInt()),
                        dstSize = playerRenderSize
                    )
                }
            }
            if (isGameOverScreen) {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column {
                        Text("Game Over", style = MaterialTheme.typography.bodyLarge, color = Color.Black)
                        Text("Score: ${timer / 200}", style = MaterialTheme.typography.bodySmall, color = Color.Black)
                    }
                }
            }
            if (!isGameStarted) {
                Box(
                    modifier = Modifier.matchParentSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "${countdown}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color.Black
                    )
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    if (!isGameOverScreen) {
        Button(
            onClick = {
                if (isGameStarted && !playerJumped) {
                    playerYVelocity = -80f
                    playerJumped = true
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.5f)
        ) {
            Text(
                "Jump",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    } else if (isGameOverScreen) {
        Button(
            onClick = {
                isGameOver = false
                isGameStarted = false
                isGameOverScreen = false
                timer = 0
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary
            ),
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .fillMaxHeight(0.5f)
        ) {
            Text(
                "Restart",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}


