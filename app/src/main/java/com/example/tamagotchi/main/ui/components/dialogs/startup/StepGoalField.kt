package com.example.tamagotchi.main.ui.components.dialogs.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.input.KeyboardType
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
        modifier = modifier.height(IntrinsicSize.Max)
    ) {
        TextField(
            value = newStepGoal.toString(),
            onValueChange = { newValue: String ->
                onStepGoalChange(newValue.toIntOrNull() ?: newStepGoal)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .clip(RoundedCornerShape(25))
                .weight(1f),
            singleLine = true,
            textStyle = MaterialTheme.typography.bodySmall
        )
        Spacer(modifier = Modifier.width(4.dp))
        Column(
            modifier = Modifier
                .weight(0.2f),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onIncrement,
                modifier = Modifier
                    .clip(RoundedCornerShape(30))
                    .background(MaterialTheme.colorScheme.secondary)
                    .weight(1f)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowUp,
                    contentDescription = "Increment Step Goal"
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            IconButton(
                onClick = onDecrement,
                modifier = Modifier
                    .clip(RoundedCornerShape(30))
                    .background(MaterialTheme.colorScheme.secondary)
                    .weight(1f)
            ) {
                Icon(
                    Icons.Default.KeyboardArrowDown,
                    contentDescription = "Decrement Step Goal"
                )
            }
        }
    }
}