package com.example.tamagotchi.main.ui.components.dialogs.startup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupDialog(
    gameViewModel: GameViewModel,
    onDismissRequest: () -> Unit,
    backPressDismiss: Boolean = false
) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    var newStepGoal by remember { mutableIntStateOf(tamagotchiState.stepGoal) }
    val bedTimeState = rememberTimePickerState(
        initialHour = tamagotchiState.bedTime.hour,
        initialMinute = tamagotchiState.bedTime.minute,
        is24Hour = true
    )

    val wakeTimeState = rememberTimePickerState(
        initialHour = tamagotchiState.wakeTime.hour,
        initialMinute = tamagotchiState.wakeTime.minute,
        is24Hour = true
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = backPressDismiss,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        ),
    ) {
        Box(
            Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(10))
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(0.25f))
                TamagotchiDisplay(tamagotchiState, modifier = Modifier.padding(16.dp))

                LazyColumn {
                    item {
                        Text(
                            text = "Set your Step goal",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            color = Color(0xffffffff),
                        )
                    }
                    item {
                        StepGoalField(
                            newStepGoal = newStepGoal,
                            onStepGoalChange = { newStepGoal = it },
                            onIncrement = { newStepGoal += 1000 },
                            onDecrement = { newStepGoal -= 1000 }
                        )
                    }
                    item {
                        Text(
                            text = "Set your Tamagotchi's bed time",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            color = Color(0xffffffff),
                        )
                    }
                    item {
                        TimeField(
                            bedTimeState,
                        )
                    }
                    item {
                        Text(
                            text = "Set your Tamagotchi's wake time",
                            style = MaterialTheme.typography.bodySmall,
                            textAlign = TextAlign.Start,
                            color = Color(0xffffffff),
                        )
                    }
                    item {
                        TimeField(
                            wakeTimeState,
                        )
                    }

                    item{Spacer(modifier = Modifier.weight(0.25f))}
                    item {
                        SubmitButton(
                            newStepGoal,
                            onDismissRequest,
                            {
                                gameViewModel.submitStepsGoal(it)
                                val bedTime = LocalTime.of(bedTimeState.hour, bedTimeState.minute)
                                gameViewModel.updateBedTime(bedTime)

                                val wakeTime =
                                    LocalTime.of(wakeTimeState.hour, wakeTimeState.minute)
                                gameViewModel.updateWakeTime(wakeTime)
                            }
                        )
                    }
                    item{Spacer(modifier = Modifier.weight(0.25f))}
                }
            }
        }
    }
}