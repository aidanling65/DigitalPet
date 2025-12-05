package com.example.tamagotchi.main.ui.components.status_bars

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun FitnessBar(state: TamagotchiState, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    val progressBar: Float = state.fitness.toFloat() / MAX_FITNESS.toFloat()
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

    Box(modifier = modifier.clickable { expanded = !expanded }) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        ) {
            LinearProgressIndicator(
                progress = { progressBar },
                color = color,
                trackColor = Color(0x00000000),
                modifier = modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.black),
                        shape = RoundedCornerShape(16.dp)
                    )
            )
            Row() {
                Text(
                    text = stringResource(R.string.fitness),
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(
                    if (expanded) Icons.Filled.KeyboardArrowDown else Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    contentDescription = "Expand fitness"
                )
            }
            if (expanded) {
                Spacer(modifier.height(8.dp))
                StatusBar(
                    progress = state.steps,
                    maximum = state.stepGoal,
                    label = stringResource(R.string.steps) + ": " + state.steps.toString(),
                    height = 16.dp,
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}