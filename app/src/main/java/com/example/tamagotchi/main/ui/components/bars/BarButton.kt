package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@Composable
fun BarButton(
    painterId: Int,
    contentDescription: String,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {

    val bitmap = ImageBitmap.imageResource(painterId)
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(8.dp)
            .aspectRatio(1f)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Image(
            bitmap = bitmap,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit,
            filterQuality = FilterQuality.None
        )
    }
}
