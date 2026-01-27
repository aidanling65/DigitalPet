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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import kotlinx.coroutines.delay

@Composable
fun AnimateDrawable(drawableRes: Int, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val animationDrawable = remember(drawableRes) {
        ContextCompat.getDrawable(context, drawableRes) as AnimationDrawable
    }

    var currentFrame by remember(drawableRes){ mutableIntStateOf(0)}

    LaunchedEffect(drawableRes) {
        val frameCount = animationDrawable.numberOfFrames
        var frame = 0
        while(true){
            currentFrame = frame
            delay(animationDrawable.getDuration(frame).toLong())
            frame = (frame + 1) % frameCount
        }

    }

    val bitmap = remember(currentFrame) {
        animationDrawable.getFrame(currentFrame).toBitmap().asImageBitmap()
    }

    Image(
        bitmap = bitmap,
        contentDescription = "animation",
        modifier = modifier,
        filterQuality= FilterQuality.None
    )
}