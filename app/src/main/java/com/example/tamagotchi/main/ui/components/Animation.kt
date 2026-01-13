package com.example.tamagotchi.main.ui.components

import android.graphics.drawable.AnimationDrawable
import android.widget.ImageView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AnimateDrawable(drawableRes: Int, modifier: Modifier = Modifier) {
    var animation: AnimationDrawable? = null

    AndroidView(
        factory = { context ->
            ImageView(context).apply {
                setBackgroundResource(drawableRes)
                animation = background as? AnimationDrawable
            }
        },
        modifier = modifier
    )

    DisposableEffect(drawableRes) {
        animation?.start()
        onDispose {
            animation?.stop()
        }
    }
}