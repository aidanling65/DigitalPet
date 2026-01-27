package com.example.tamagotchi.minigames

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.utils.animation.getAnimationFrames
import kotlinx.coroutines.delay

@Composable
fun JumpGameCanvas(
    tamagotchiState: TamagotchiState,
    gameViewModel: GameViewModel,
    restartFun: () -> Unit
) {

    val context = LocalContext.current
    val playerAnimationFrames = remember(tamagotchiState.animations.play) {
        getAnimationFrames(context, tamagotchiState.animations.play)
    }

    var canvasSize by remember { mutableStateOf(Size.Zero) }

    var canvasHeight by remember { mutableFloatStateOf(1000f) }
    var canvasWidth by remember { mutableFloatStateOf(1000f) }

    var laps = 0L
    var currentFrame by remember { mutableIntStateOf(0) }
    if (playerAnimationFrames.isEmpty()) {
        Text("Error: Could not load player animation.")
        return
    }

    val playerBitmap = playerAnimationFrames[currentFrame]
    val player = remember {
        Obstacle(
            x = 100f,
            y = 0f,
            width = playerBitmap.width.toFloat(),
            height = playerBitmap.height.toFloat(),
            yAcceleration = 4f
        )
    }
    var playerJumped by remember { mutableStateOf(false) }

    val obstacle = remember {
        Obstacle(
            x = 0f,
            y = 0f,
            width = 30f,
            height = 0f,
            xVelocity = -10f
        )
    }


    var timer by remember { mutableLongStateOf(0L) }
    var isGameOver by remember { mutableStateOf(false) }
    var isGameOverScreen by remember { mutableStateOf(false) }
    var isGameStarted by remember { mutableStateOf(false) }
    val delay = 16L
    val frameRate = 500L / delay

    fun restart() {
        isGameOver = false
        isGameStarted = false
        isGameOverScreen = false
        timer = 0
        obstacle.x
        player.y = canvasSize.height - player.height
        player.yVelocity = 0f
        obstacle.xVelocity = -10f
        restartFun()
    }

    LaunchedEffect(isGameStarted) {
        if (!isGameStarted) return@LaunchedEffect
        while (true) {
            if (isGameOver) {
                gameViewModel.gameScore((timer / 150L).toInt())
                return@LaunchedEffect
            }
            player.maxY = canvasSize.height
            player.move()
            if (player.y == canvasSize.height - player.height) {
                playerJumped = false
            }

            obstacle.move()
            if (obstacle.x < -obstacle.width) {
                obstacle.x = canvasSize.width

            }
            laps += 1
            if (laps % frameRate == 0L) {
                currentFrame = (currentFrame + 1) % playerAnimationFrames.size
                laps = 0
                obstacle.xVelocity -= 0.02f
            }


            if (obstacle.isTouching(player)) {
                Log.d(
                    "Collision",
                    "Player: (${player.x}, ${player.y})\nObstacle: (${obstacle.x}, ${obstacle.y}"
                )
                isGameOver = true
                delay(500L)
                isGameOverScreen = true
            }

            timer += delay

            delay(delay)
        }
    }

    var countdown by remember { mutableIntStateOf(3) }
    if (!isGameStarted) {
        if (canvasSize != Size.Zero) {
            obstacle.x = canvasWidth
            player.y = canvasHeight - player.height
        }
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
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScoreBoard((timer / 200).toInt(), isGameStarted && !isGameOver)
        Box(
            Modifier
                .clip(RoundedCornerShape(10))
                .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                .background(colorResource(R.color.lcd))
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {

                if (canvasSize == Size.Zero) {
                    canvasSize = size
                }

                obstacle.height = size.height / 10
                obstacle.y = size.height - obstacle.height

                if (!isGameOverScreen) {
                    if (isGameStarted) {
                        drawRect(
                            Color(0xFF000000),
                            topLeft = Offset(obstacle.x, obstacle.y),
                            size = Size(obstacle.width, obstacle.height)
                        )
                    }

                    drawImage(
                        image = playerBitmap,
                        srcOffset = IntOffset.Zero,
                        srcSize = IntSize(playerBitmap.width, playerBitmap.height),
                        dstOffset = IntOffset(player.x.toInt(), player.y.toInt()),
                        dstSize = IntSize(player.width.toInt(), player.height.toInt())
                    )
                }
            }
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                GameOverScreen(isGameOverScreen, (timer / 200).toInt())
                Countdown(isGameStarted, countdown)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    GameButtons(isGameOverScreen, isGameStarted, { restart() }, jumpAction = {
        if (!playerJumped) {
            player.yVelocity = -35f
            playerJumped = true
        }
    })
}

