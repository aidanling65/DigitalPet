package com.example.digitalpet.main.utils.animation

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

fun getAnimationDuration(context: Context, animationId: Int): Int{
    val animationDrawable = AppCompatResources.getDrawable(context, animationId) as? AnimationDrawable
    if (animationDrawable == null) {
        return 0
    }
    var count = 0
    for(i in 0 until animationDrawable.numberOfFrames){
        count += animationDrawable.getDuration(i)
    }
    return count
}