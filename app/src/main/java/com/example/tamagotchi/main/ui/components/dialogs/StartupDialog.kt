package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun StartupDialog(
    tamagotchiState: TamagotchiState,
    onDismissRequest: () -> Unit,
    submitSteps: (Int) -> Unit,
    incrementStepGoal: () -> Unit,
    decrementStepGoal: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = false,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Box(
            Modifier.fillMaxSize().padding(horizontal = 8.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.8f)
                    .background(MaterialTheme.colorScheme.background),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                TamagotchiDisplay(tamagotchiState, modifier = Modifier.padding(16.dp))
                Text(
                    text = "Set your Step goal",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xffffffff),
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    TextField(
                        value = tamagotchiState.stepGoal.toString(),
                        onValueChange = { newValue: String ->
                            submitSteps(
                                newValue.toIntOrNull() ?: 0
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(0.1f).padding(horizontal = 8.dp),
                        singleLine = true
                    )
                    Column() {
                        IconButton(onClick = incrementStepGoal) {
                            Icon(
                                Icons.Default.KeyboardArrowUp,
                                contentDescription = "Increment Step Goal"
                            )
                        }
                        IconButton(onClick = decrementStepGoal) {
                            Icon(
                                Icons.Default.KeyboardArrowDown,
                                contentDescription = "Decrement Step Goal"
                            )
                        }
                    }
                }

                IconButton(
                    onClick = onDismissRequest,
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
    }
}
