package com.example.tamagotchi.main.utils.animation

import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
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

    var bitmap: ImageBitmap? by remember { mutableStateOf(null) }

    LaunchedEffect(key) {
        val frameCount = animDrawable.numberOfFrames
        if (frameCount > 0) {

            val initialDrawable = animDrawable.getFrame(0)
            bitmap = initialDrawable.toBitmap(
                width = initialDrawable.intrinsicWidth,
                height = initialDrawable.intrinsicHeight,
                config = Bitmap.Config.ARGB_8888
            ).copy(Bitmap.Config.ARGB_8888, true).asImageBitmap()
            for (frame in 0 until frameCount) {
                delay(animDrawable.getDuration(frame).toLong())
                val drawable = animDrawable.getFrame(frame)
                bitmap = drawable.toBitmap(
                    width = initialDrawable.intrinsicWidth,
                    height = initialDrawable.intrinsicHeight,
                    config = Bitmap.Config.ARGB_8888
                ).copy(Bitmap.Config.ARGB_8888, true).asImageBitmap()
                Log.d("OneShot", "frame change: $frame")
            }
        }
        onAnimationFinish()
    }

    bitmap?.let {
        Image(
            bitmap = it,
            contentDescription = "animation_oneshot",
            modifier = modifier,
            filterQuality = FilterQuality.None
        )
    }
}
