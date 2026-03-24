package com.example.tamagotchi.main.utils.animation

import android.graphics.drawable.AnimationDrawable
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.example.tamagotchi.R
import kotlinx.coroutines.delay

@Composable
fun AnimateDrawable(
    drawableRes: Int,
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF869484),
) {
    val context = LocalContext.current
    val animationDrawable = remember(drawableRes) {
        ContextCompat.getDrawable(context, drawableRes) as AnimationDrawable
    }

    var currentFrame by remember(drawableRes) { mutableIntStateOf(0) }

    LaunchedEffect(drawableRes) {
        val frameCount = animationDrawable.numberOfFrames
        var frame = 0
        while (true) {
            currentFrame = frame
            delay(animationDrawable.getDuration(frame).toLong())
            frame = (frame + 1) % frameCount
        }

    }

    val bitmap = remember(currentFrame) {
        animationDrawable.getFrame(currentFrame).toBitmap().asImageBitmap()
    }


    val tintMatrix = ColorMatrix(
        floatArrayOf(
            color.red, 0f, 0f, 0f, 1f,
            0f, color.green, 0f, 0f, 1f,
            0f, 0f, color.blue, 0f, 1f,
            0f, 0f, 0f, 1f, 0f
        )
    )
    Image(
        bitmap = bitmap,
        contentDescription = "animation",
        modifier = modifier,
        filterQuality = FilterQuality.None,
        colorFilter = ColorFilter.colorMatrix(tintMatrix)
    )
}

@Preview()
@Composable
fun AnimateDrawablePreview() {
    AnimateDrawable(R.drawable.adult_2_eating)
}