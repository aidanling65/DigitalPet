package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp

@Composable
fun ManualEntry(
    title: String,
    content: @Composable () -> Unit,
    painterId: Int? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var bitmap: ImageBitmap? = null
    if (painterId != null) {
        bitmap = ImageBitmap.imageResource(painterId)
    }
    var textColor = MaterialTheme.colorScheme.primary
    if (expanded) {
        textColor = Color.Black
    }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(color = if (expanded) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.secondary)
                .clickable(onClick = { expanded = !expanded })
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                Row(
                    Modifier.wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Spacer(Modifier.width(8.dp))
                    if (bitmap != null) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                        Spacer(Modifier.width(8.dp))
                    } else {
                        Spacer(Modifier.width(40.dp))
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodyMedium,
                        color = textColor,
                        modifier = Modifier.weight(1f),
                    )
                }
                AnimatedVisibility(visible = expanded) {
                    content()
                }
            }
        }
    }
}