package com.example.tamagotchi.main.utils.animation

import android.graphics.drawable.AnimationDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.delay

@Composable
fun AnimateDrawableOneShot(
    drawableRes: Int,
    key: Any?,
    modifier: Modifier = Modifier,
    onAnimationFinish: () -> Unit
) {
    val context = LocalContext.current
    val animDrawable = remember(drawableRes) {
        ContextCompat.getDrawable(context, drawableRes) as AnimationDrawable
    }

    val totalDuration = remember(animDrawable) {
        (0 until animDrawable.numberOfFrames).sumOf { animDrawable.getDuration(it) }.toLong()
    }

    var currentFrame by remember(drawableRes) { mutableStateOf(0) }

    LaunchedEffect(key) {
        val frameCount = animDrawable.numberOfFrames
        var frame = 0
        while (frame < frameCount) {
            currentFrame = frame
            delay(animDrawable.getDuration(frame).toLong())
            frame++
        }
    }

    LaunchedEffect(key) {
        if (totalDuration > 0) {
            delay(totalDuration)
            onAnimationFinish()
        }
    }

    val bitmap = remember(currentFrame) {
        animDrawable.getFrame(currentFrame).toBitmap().asImageBitmap()
    }

    Image(
        bitmap = bitmap,
        contentDescription = "Eating Animation",
        modifier = modifier,
        filterQuality = FilterQuality.None
    )
}
