package com.example.tamagotchi.main.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import kotlinx.coroutines.delay
import kotlin.random.Random

enum class ConfettiSource {
    TOP, BOTTOM, LEFT, RIGHT, CENTER
}

data class Confetti(var x: Float, var y: Float, val color: Color, var velocityY: Float = 0f, var velocityX: Float = 0f)

@Composable
fun ConfettiView(source: ConfettiSource = ConfettiSource.CENTER, quantity: Int = 50, modifier: Modifier = Modifier) {
    val density = LocalDensity.current
    val window = LocalWindowInfo.current

    val screenWidthPx = with(density) { window.containerSize.width.toFloat() }
    val screenHeightPx = with(density) { window.containerSize.height.toFloat() }

    var confettiList by remember { mutableStateOf(List(quantity) { createConfetti(source, screenWidthPx, screenHeightPx) }) }
    var isRunning by remember { mutableStateOf(true) }

    LaunchedEffect(isRunning) {
        while (isRunning) {
            confettiList = confettiList.map { confetti ->
                confetti.y += confetti.velocityY
                confetti.x += confetti.velocityX
                confetti.velocityY += 0.5f
                if (confetti.y > screenHeightPx * 1.2f || confetti.x < -100f || confetti.x > screenWidthPx + 100f) {
                    createConfetti(source, screenWidthPx, screenHeightPx)
                } else {
                    confetti
                }
            }
            delay(16)
        }
    }

    Canvas(modifier = modifier.fillMaxSize()) {
        confettiList.forEach { confetti ->
            drawCircle(
                color = confetti.color,
                radius = 12f,
                center = Offset(confetti.x, confetti.y),
                style = Fill
            )
        }
    }
}

fun createConfetti(source: ConfettiSource, screenWidth: Float, screenHeight: Float): Confetti {
    val x = when (source) {
        ConfettiSource.LEFT -> -50f
        ConfettiSource.RIGHT -> screenWidth + 50f
        ConfettiSource.CENTER -> Random.nextFloat() * screenWidth
        else -> Random.nextFloat() * screenWidth
    }
    val y = when (source) {
        ConfettiSource.TOP -> -50f
        ConfettiSource.BOTTOM -> screenHeight + 50f
        ConfettiSource.CENTER -> Random.nextFloat() * screenHeight
        else -> if (source == ConfettiSource.LEFT || source == ConfettiSource.RIGHT) Random.nextFloat() * screenHeight else -50f
    }
    val velocityX = when (source) {
        ConfettiSource.LEFT -> Random.nextFloat() * 10 + 5
        ConfettiSource.RIGHT -> Random.nextFloat() * -10 - 5
        else -> Random.nextFloat() * 20 - 10
    }
    val velocityY = when (source) {
        ConfettiSource.BOTTOM -> Random.nextFloat() * -10 - 5
        else -> Random.nextFloat() * 10 - 5
    }
    return Confetti(
        x = x,
        y = y,
        color = Color(Random.nextInt()),
        velocityY = velocityY,
        velocityX = velocityX
    )
}