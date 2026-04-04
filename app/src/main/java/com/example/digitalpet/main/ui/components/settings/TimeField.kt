package com.example.digitalpet.main.ui.components.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeField(
    state: TimePickerState,
    modifier: Modifier = Modifier,
    height: Dp = 48.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(height)
            .clip(RoundedCornerShape(25))
            .padding(vertical = 8.dp)
    ) {
        UpDownButtons(
            { state.hour = if (state.hour == 0) 23 else (state.hour - 1) % 24 },
            { state.hour = (state.hour + 1) % 24 },
            Modifier.fillMaxHeight()
        )
        BasicTextField(
            value = state.hour.toString().padStart(2, '0'),
            onValueChange = { newValue: String ->
                state.hour = newValue.toIntOrNull() ?: state.hour
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        ){ innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(4.dp)
                    .clip(RoundedCornerShape(25))
                    .background(MaterialTheme.colorScheme.secondary)
            ){
                innerTextField()
            }
        }
        Text(
            text = ":",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            modifier=Modifier.fillMaxHeight()
        )
        BasicTextField(
            value = state.minute.toString().padStart(2, '0'),
            onValueChange = { newValue: String ->
                state.minute = newValue.toIntOrNull() ?: state.minute
            },
            textStyle = MaterialTheme.typography.bodySmall.copy(
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier
                .fillMaxHeight()
                .weight(0.5f),
            singleLine = true,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        ){ innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .shadow(4.dp)
                    .clip(RoundedCornerShape(25))
                    .background(MaterialTheme.colorScheme.secondary)
            ){
                innerTextField()
            }
        }
        UpDownButtons(
            { state.minute = if(state.minute==0) 59 else (state.minute - 1) % 60 },
            { state.minute = (state.minute + 1) % 60 },
            Modifier.fillMaxHeight()
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TimeFieldPreview(){
    TimeField(TimePickerState(10 ,0, true))
}