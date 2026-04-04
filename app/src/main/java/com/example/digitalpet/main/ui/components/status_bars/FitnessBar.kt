package com.example.digitalpet.main.ui.components.status_bars

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.digitalpet.R
import com.example.digitalpet.main.data.model.MAX_FITNESS
import com.example.digitalpet.main.data.model.PetState

@Composable
fun FitnessBar(state: PetState, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

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
            StatusBar(
                progress = state.fitness,
                maximum = MAX_FITNESS,
                labelComplex = {
                    Row {
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
                }
            )
            if (expanded) {
                Spacer(modifier.height(8.dp))
                StatusBar(
                    progress = state.steps,
                    maximum = state.stepGoal,
                    label = stringResource(R.string.steps) + ": ${state.steps} / ${state.stepGoal}",
                    height = 12.dp,
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}