package com.example.tamagotchi.minigames

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
fun FlappyGameCanvas(
    tamagotchiState: TamagotchiState,
    gameViewModel: GameViewModel,
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
            playerBitmap.width.toFloat(),
            playerBitmap.height.toFloat(),
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
            xVelocity = -10f
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

        restartFun()
    }

    LaunchedEffect(isGameStarted) {

        if (!isGameStarted) return@LaunchedEffect
        while (true) {
            if (isGameOver) {
                gameViewModel.gameScore(score)
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
            laps += 1
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
            player.width = playerAnimationFrames[0].width.toFloat()
            player.height = playerAnimationFrames[0].height.toFloat()
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

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        ScoreBoard(score, isGameStarted && !isGameOver)
        Box(
            Modifier
                .clip(RoundedCornerShape(10))
                .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                .background(colorResource(R.color.lcd))
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            Canvas(modifier = Modifier.matchParentSize()) {
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
                            dstSize = IntSize(playerBitmap.width, playerBitmap.height)
                        )
                    }
                }
            }
            Box(modifier = Modifier.matchParentSize(), contentAlignment = Alignment.Center) {
                GameOverScreen(isGameOverScreen, score)
                Countdown(isGameStarted, countdown)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    GameButtons(isGameOverScreen, isGameStarted, { resetGame() }, { player.yVelocity = -17f })
}