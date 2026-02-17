package com.example.tamagotchi.intelligence.nonogram.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateIntOffsetAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons(blocking: Boolean, modifier: Modifier = Modifier, onClick: () -> Unit) {

    val notBlockingColor by animateColorAsState(
        targetValue = if(!blocking) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
    )

    val blockingColor by animateColorAsState(
        targetValue = if(blocking) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onBackground
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxWidth(0.4f)
            .height(48.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
            .clickable(
                onClick = onClick,
                enabled = true,
                interactionSource = null,
                indication = null
            ),
    ) {
        val pxToMove = with(LocalDensity.current){(maxWidth/2).toPx().toInt()}

        val offset by animateIntOffsetAsState(
            targetValue = if(blocking) IntOffset(0,0) else IntOffset(pxToMove, 0),
            label = "offsetAnimation"
        )
        Box(
            Modifier
                .offset{offset}
                .shadow(elevation = 4.dp, RoundedCornerShape(50))
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .background(MaterialTheme.colorScheme.onBackground)
                .clip(RoundedCornerShape(50)),
        )
        Row(Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                Icons.Default.Clear,
                contentDescription = "Clear",
                modifier = Modifier.padding(end = 4.dp).weight(1f),
                colorFilter = ColorFilter.tint(blockingColor)
            )
            Image(
                Icons.Default.Check,
                contentDescription = "Check",
                modifier = Modifier.padding(end = 4.dp).weight(1f),
                colorFilter = ColorFilter.tint(notBlockingColor)
            )
        }
    }
}