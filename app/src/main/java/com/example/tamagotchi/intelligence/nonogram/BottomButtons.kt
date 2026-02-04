package com.example.tamagotchi.intelligence.nonogram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp

@Composable
fun BottomButtons(blocking: Boolean, modifier:Modifier = Modifier, onClick: ()->Unit){
    Row(
        modifier = modifier
            .fillMaxWidth(0.4f)
            .height(48.dp)
            .clip(RoundedCornerShape(50))
            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f))
            .clickable { onClick() },
    ) {
        Box(
            Modifier
                .fillMaxHeight()
                .weight(1f)
                .shadow(elevation = if (blocking) 2.dp else 0.dp)
                .background(if (blocking) Color.White else Color.Transparent)
                .clip(RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                Icons.Default.Clear,
                contentDescription = "Clear",
                modifier = Modifier.padding(end = 4.dp),
                colorFilter = if (blocking) ColorFilter.tint(MaterialTheme.colorScheme.secondary) else ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )
        }
        Box(
            Modifier
                .fillMaxHeight()
                .weight(1f)
                .shadow(elevation = if (!blocking) 2.dp else 0.dp)
                .background(if (!blocking) Color.White else Color.Transparent)
                .clip(RoundedCornerShape(50)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                Icons.Default.Check,
                contentDescription = "Check",
                modifier = Modifier.padding(end = 4.dp),
                colorFilter = if (!blocking) ColorFilter.tint(MaterialTheme.colorScheme.secondary) else ColorFilter.tint(
                    MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}