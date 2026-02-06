package com.example.tamagotchi.minigames

import androidx.compose.foundation.Canvas
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

@Composable
fun FlappyGameCanvas(
    tamagotchiState: TamagotchiState,
    gameScore: (Int) -> Unit,
    restartFun: () -> Unit
) {
    val context = LocalContext.current

    val canvasSize = remember { mutableStateOf(Size(0f, 0f)) }

    var frame by remember { mutableLongStateOf(0L) }

    var laps = 0L
    var currentFrame by remember { mutableIntStateOf(0) }
    val playerAnimationFrames = remember(tamagotchiState.animations.play) {
        getAnimationFrames(context, tamagotchiState.animations.play)
    }
    val playerBitmap = playerAnimationFrames[currentFrame]

    val player = remember {
        Obstacle(
            100f,
            canvasSize.value.height / 2,
            128f,
            128f,
            xVelocity = 0f,
            yVelocity = 0f,
            xAcceleration = 0f,
            yAcceleration = 1.25f,
            yTerminalVelocity = 20f
        )
    }

    var obstacleHeightOffset by remember { mutableFloatStateOf(0f) }
    var gapSize by remember { mutableFloatStateOf(0f) }

    val obstacle = remember {
        Obstacle(
            -500f,
            0f,
            80f,
            500f,
            xVelocity = tamagotchiState.gameDifficulty.flappyVelocity
        )
    }
    val obstacle2 = remember {
        Obstacle(
            -500f,
            0f,
            obstacle.width,
            obstacle.height,
            xVelocity = obstacle.xVelocity
        )
    }

    var score by remember { mutableIntStateOf(0) }
    var isGameOver by remember { mutableStateOf(false) }
    var isGameOverScreen by remember { mutableStateOf(false) }
    var isGameStarted by remember { mutableStateOf(false) }

    val delay = 16L
    val frameRate = 300L / delay

    var countdown by remember { mutableIntStateOf(3) }

    fun resetGame() {
        isGameOver = false
        isGameOverScreen = false
        isGameStarted = false
        score = 0
        countdown = 3
        player.y = canvasSize.value.height / 2 - player.height
        obstacle.x = canvasSize.value.width
        obstacle2.x = canvasSize.value.width
        obstacle.xVelocity =  tamagotchiState.gameDifficulty.flappyVelocity
        obstacle2.xVelocity = obstacle.xVelocity

        restartFun()
    }

    LaunchedEffect(isGameStarted) {

        if (!isGameStarted) return@LaunchedEffect
        while (true) {
            if (isGameOver) {
                gameScore(score)
                delay(500L)
                return@LaunchedEffect
            }
            player.move()

            obstacle.move()
            obstacle2.move()

            if (obstacle.x < -obstacle.width) {
                obstacle.x = canvasSize.value.width
                obstacle2.x = canvasSize.value.width
                obstacleHeightOffset =
                    ((-(canvasSize.value.height / 2).toInt()..100).random()).toFloat()
            }
            laps++
            if (laps % frameRate == 0L) {
                currentFrame = (currentFrame + 1) % playerAnimationFrames.size
            }

            if (obstacle.isTouching(player, 10f) || obstacle2.isTouching(player, 10f)) {
                isGameOver = true
                delay(500L)
                isGameOverScreen = true
            } else if (player.y > canvasSize.value.height + 200) {
                isGameOver = true
                delay(500L)
                isGameOverScreen = true
            }
            if (player.x > obstacle.x) {
                score += 1
            }

            frame++
            delay(delay)
        }
    }

    if (!isGameStarted && !isGameOver) {
        LaunchedEffect(canvasSize.value.width, isGameStarted, isGameOver) {
            if (canvasSize.value.width == 0f) return@LaunchedEffect

            player.y = canvasSize.value.height / 2 - player.height
            obstacle.x = canvasSize.value.width
            obstacle2.x = canvasSize.value.width
            player.height = canvasSize.value.height / 6f
            player.width = player.height * playerBitmap.width / playerBitmap.height
            gapSize = player.height * 3f
            while (countdown > 0) {
                delay(1000L)
                countdown -= 1
            }
            if (!isGameOver) {
                isGameStarted = true
            }
        }
    }

    MinigameBase(
        title = "Flappagotchi",
        score = score,
        isGameStarted,
        isGameOver,
        isGameOverScreen,
        countdown,
        { resetGame() },
        { player.yVelocity = -17f}
    ) {modifier ->
        Canvas(modifier = modifier) {
            frame.let {
                if (canvasSize.value.width == 0f) {
                    canvasSize.value = size
                }
                obstacle.height = (canvasSize.value.height / 2) + obstacleHeightOffset
                obstacle2.y = obstacle.bottom + gapSize
                obstacle2.height = canvasSize.value.height - obstacle2.y
                if (!isGameOverScreen) {
                    if (isGameStarted) {
                        drawRect(
                            Color(0xFF000000),
                            topLeft = Offset(obstacle.x, obstacle.y),
                            size = Size(obstacle.width, obstacle.height)
                        )
                        drawRect(
                            Color(0xFF000000),
                            topLeft = Offset(obstacle2.x, obstacle2.y),
                            size = Size(obstacle2.width, obstacle2.height)
                        )
                    }

                    drawImage(
                        image = playerAnimationFrames[currentFrame],
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