package com.example.tamagotchi.main.ui.components.dialogs.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StepGoalField(
    newStepGoal: Int,
    onStepGoalChange: (Int) -> Unit,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth(0.5f)
            .height(48.dp)
            .padding(vertical=8.dp)
    ) {
        BasicTextField(
            value = newStepGoal.toString(),
            onValueChange = { newValue: String ->
                onStepGoalChange(newValue.toIntOrNull() ?: newStepGoal)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .padding(start = 8.dp)
                .clip(RoundedCornerShape(25))
                .fillMaxHeight()
                .weight(0.8f),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.primary, textAlign = TextAlign.Center),
        ){ innerTextField ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(25))
                    .background(MaterialTheme.colorScheme.secondary)
            ){
                innerTextField()
            }

        }
        UpDownButtons(onDecrement, onIncrement, modifier = Modifier.fillMaxHeight())
    }
}

@Preview(showBackground = true)
@Composable
fun StepGoalPreview() {
    StepGoalField(1000, {}, {}, {})
}
