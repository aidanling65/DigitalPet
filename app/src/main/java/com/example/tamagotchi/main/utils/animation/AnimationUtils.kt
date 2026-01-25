package com.example.tamagotchi.main.utils.animation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.AnimationDrawable
import androidx.appcompat.content.res.AppCompatResources
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.core.graphics.drawable.toBitmap

fun getAnimationFrames(context: Context, animationId: Int): List<ImageBitmap> {
    val animationDrawable = AppCompatResources.getDrawable(context, animationId) as? AnimationDrawable
    if (animationDrawable == null) {
        val singleDrawable = AppCompatResources.getDrawable(context, animationId)
        return singleDrawable?.let {
            listOf(it.toBitmap().asImageBitmap())
        } ?: emptyList()
    }

    val frames = mutableListOf<ImageBitmap>()
    for (i in 0 until animationDrawable.numberOfFrames) {
        val frame = animationDrawable.getFrame(i)
        val bitmap = frame.toBitmap(
            width = frame.intrinsicWidth.takeIf { it > 0 } ?: 100,
            height = frame.intrinsicHeight.takeIf { it > 0 } ?: 100,
            config = Bitmap.Config.ARGB_8888
        )
        frames.add(bitmap.asImageBitmap())
    }
    return frames
}