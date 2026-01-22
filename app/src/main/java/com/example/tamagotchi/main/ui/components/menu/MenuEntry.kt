package com.example.tamagotchi.main.ui.components.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@Composable
fun MenuEntry(imageId: Int, text: String, visible: Boolean) {
    val bitmap = ImageBitmap.imageResource(imageId)
    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            ),
            initialOffsetX = { fullWidth -> 2 * fullWidth }
        ),
        exit = slideOutHorizontally(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy,
                stiffness = Spring.StiffnessVeryLow
            ),
            targetOffsetX = { fullWidth -> 2 * fullWidth }
        )
    ) {
        Box(
            modifier = Modifier.clipToBounds().padding(start = 16.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .height(48.dp)
                        .clip(
                            RoundedCornerShape(
                                topStart = 50f,
                                bottomStart = 50f,
                                topEnd = 0f,
                                bottomEnd = 0f
                            )
                        )
                        .background(MaterialTheme.colorScheme.secondary)
                        .fillMaxWidth()
                        .padding(start = 16.dp, end=36.dp)
                ) {
                    Image(
                        bitmap = bitmap,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(0.25f)
                            .padding(end = 8.dp),
                        contentScale = ContentScale.Fit,
                        filterQuality = FilterQuality.None
                    )
                    Text(
                        text = text,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(0.75f)
                    )
                }
            }
        }
    }
}