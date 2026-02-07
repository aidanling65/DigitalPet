package com.example.tamagotchi.main.ui.components.dialogs.startup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color

@Composable
fun SubmitButton(newStepGoal: Int, modifier:Modifier=Modifier, onDismissRequest: () -> Unit, submitSteps: (Int) -> Unit){
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        IconButton(
            onClick = {
                Log.d("StartupDialog", "Step goal set to $newStepGoal")
                submitSteps(newStepGoal)
                onDismissRequest()
            },
            content = {
                Row(
                    Modifier
                        .clip(RoundedCornerShape(10))
                        .background(Color(0xFF396C39))
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Submit"
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(0.75f)
        )
    }
}