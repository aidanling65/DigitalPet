package com.example.tamagotchi.main.ui.components.status_bars

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R

@Composable
fun StatusBar(
    progress: Int,
    maximum: Int,
    modifier: Modifier = Modifier,
    label: String? = null,
    labelComplex: (@Composable () -> Unit)? = null,
    height: Dp = 16.dp,
) {
    val progressBar: Float = progress.toFloat() / maximum
    val color = when {
        progressBar <= 0.5f -> {
            val factor = progressBar / 0.5f
            Color(
                red = 1f,
                green = factor,
                blue = 0f
            )
        }

        else -> {
            val factor = (progressBar - 0.5f) / 0.5f
            Color(
                red = 1f - factor,
                green = 1f,
                blue = 0f
            )
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        if(labelComplex != null){
            labelComplex()
        }else if(label != null){
            Text(
                text = label,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.bodySmall,
                modifier = modifier
            )
        }
        LinearProgressIndicator(
            progress = { progressBar },
            color = color,
            trackColor = Color(0x00000000),
            modifier = modifier
                .fillMaxWidth()
                .height(height)
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.black),
                    shape = RoundedCornerShape(16.dp)
                )
        )

    }
}
