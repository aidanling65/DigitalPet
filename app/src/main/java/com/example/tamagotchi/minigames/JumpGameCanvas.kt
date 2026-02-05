package com.example.tamagotchi.minigames

import android.util.Log
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.animation.getAnimationFrames
import com.example.tamagotchi.minigames.ui.MinigameBase
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun JumpGameCanvas(
    tamagotchiState: TamagotchiState,
    gameScore: (Int) -> Unit,
    restartFun: () -> Unit
) {

    val context = LocalContext.current
    val playerAnimationFrames = remember(tamagotchiState.animations.play) {
        getAnimationFrames(context, tamagotchiState.animations.play)
    }

    var canvasSize by remember { mutableStateOf(Size.Zero) }
    var canvasHeight by remember { mutableFloatStateOf(1000f) }
    var canvasWidth by remember { mutableFloatStateOf(1000f) }

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
            width = 128f,
            height = canvasHeight / 2,
            yAcceleration = 6f
        )
    }
    var playerJumped by remember { mutableStateOf(false) }

    var minXVelocity = tamagotchiState.gameDifficulty.obstacleInitialMinVelocity
    var maxXVelocity = tamagotchiState.gameDifficulty.obstacleInitialMaxVelocity
    val obstacle = remember {
        Obstacle(
            x = 0f,
            y = 0f,
            width = 30f,
            height = 0f,
            xVelocity = tamagotchiState.gameDifficulty.obstacleInitialMinVelocity,
            xTerminalVelocity = 25f
        )
    }

    var timer by remember { mutableLongStateOf(0L) }
    var score by remember { mutableIntStateOf(0) }
    var frame by remember { mutableLongStateOf(0L) }
    var isGameOver by remember { mutableStateOf(false) }
    var isGameOverScreen by remember { mutableStateOf(false) }
    var isGameStarted by remember { mutableStateOf(false) }
    var countdown by remember { mutableIntStateOf(3) }

    fun restart() {
        isGameOver = false
        isGameStarted = false
        isGameOverScreen = false
        timer = 0
        score = 0
        countdown = 3
        obstacle.x = canvasSize.width
        player.y = player.maxY - player.height
        player.yVelocity = 0f
        obstacle.xVelocity = -15f
        minXVelocity = tamagotchiState.gameDifficulty.obstacleInitialMinVelocity
        maxXVelocity = tamagotchiState.gameDifficulty.obstacleInitialMaxVelocity
        restartFun()
    }

    if (!isGameStarted && !isGameOver) {
        LaunchedEffect(canvasSize.width, isGameStarted, isGameOver) {
            if (canvasSize.width == 0f || isGameStarted || isGameOver) return@LaunchedEffect
            player.height = canvasSize.height / 5f
            player.width = player.height * playerBitmap.width / playerBitmap.height
            obstacle.x = canvasWidth
            player.y = canvasHeight - player.height - 60f

            while (countdown > 0) {
                delay(1000L)
                countdown -= 1
            }
            if (!isGameOver) {
                isGameStarted = true
            }
        }
    }

    val delay = 16L
    var laps = 0L
    val frameRate = 300L / delay
    LaunchedEffect(isGameStarted) {
        if (!isGameStarted) return@LaunchedEffect
        while (true) {
            if (isGameOver) {
                gameScore(score)
                return@LaunchedEffect
            }
            if (player.bottom <= player.maxY) {
                playerJumped = false
            }

            obstacle.move()
            player.move()
            if (obstacle.x < -obstacle.width) {
                obstacle.x = canvasSize.width
                minXVelocity -= tamagotchiState.gameDifficulty.obstacleAcceleration
                maxXVelocity -= tamagotchiState.gameDifficulty.obstacleAcceleration
                obstacle.xVelocity =
                    Random.nextFloat() * (maxXVelocity - minXVelocity) + minXVelocity
            }

            laps++
            if (laps % frameRate == 0L) {
                currentFrame = (currentFrame + 1) % playerAnimationFrames.size
            }

            if (obstacle.isTouching(player)) {
                Log.d(
                    "Collision",
                    "Player: (${player.x}, ${player.y})\nObstacle: (${obstacle.x}, ${obstacle.y})"
                )
                isGameOver = true
                delay(500L)
                isGameOverScreen = true
            }

            timer += delay
            score = (timer / 100).toInt()
            frame++
            delay(delay)
        }
    }

    MinigameBase(
        title = "Jumpagotchi",
        score = score,
        isGameStarted,
        isGameOver,
        isGameOverScreen,
        countdown,
        { restart() },
        {
            if (isGameStarted && !isGameOver && !playerJumped) {
                playerJumped = true
                player.yVelocity = -95f
            }
        }
    ) { modifier ->
        Canvas(modifier = modifier) {
            frame.let {
                if (canvasSize == Size.Zero) {
                    canvasSize = size
                }

                player.maxY = size.height
                if (player.y >= size.height - player.height) {
                    player.y = size.height - player.height
                    player.yVelocity = 0f
                    playerJumped = false
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
                        dstSize = IntSize(player.width.toInt(), player.height.toInt()),
                        filterQuality = FilterQuality.None
                    )
                }
            }
        }
    }
}

