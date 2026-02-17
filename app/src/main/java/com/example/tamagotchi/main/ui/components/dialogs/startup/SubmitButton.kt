package com.example.tamagotchi.main.ui.components.dialogs.startup

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SubmitButton(
    newStepGoal: Int,
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    submitSteps: (Int) -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Button(
            onClick = {
                Log.d("StartupDialog", "Step goal set to $newStepGoal")
                submitSteps(newStepGoal)
                onDismissRequest()
            },
            shape = RoundedCornerShape(50),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp, pressedElevation = 0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1FA71F)),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier.fillMaxWidth(0.75f)
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Submit",
                tint = Color.White
            )
        }
    }
}