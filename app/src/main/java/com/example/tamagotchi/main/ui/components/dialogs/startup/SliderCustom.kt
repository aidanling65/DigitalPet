package com.example.tamagotchi.main.ui.components.dialogs.startup

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SliderCustom(
    value: Float,
    steps: Int,
    range: ClosedFloatingPointRange<Float>,
    modifier: Modifier = Modifier,
    onValueChange: (Float) -> Unit
) {
    Slider(
        value = value,
        onValueChange = { onValueChange(it) },
        thumb = {
            SliderDefaults.Thumb(
                interactionSource = MutableInteractionSource(),
                modifier = Modifier
                    .shadow(8.dp, CircleShape)
                    .clip(
                        CircleShape
                    ),
                thumbSize = DpSize(24.dp, 24.dp),
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.secondary,
                )
            )
        },
        track = { sliderState ->
            SliderDefaults.Track(
                sliderState = sliderState,
                modifier = Modifier.shadow(2.dp, RoundedCornerShape(50)),
                colors = SliderDefaults.colors(
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                thumbTrackGapSize = 0.dp,
            )
        },
        steps = steps,
        valueRange = range,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun SliderPreview() {
    SliderCustom(0f, 3, 0f..3f) { }
}

@Preview(showBackground = true)
@Composable
fun SliderPreview2() {
    SliderCustom(1f, 3, 0f..3f) { }
}