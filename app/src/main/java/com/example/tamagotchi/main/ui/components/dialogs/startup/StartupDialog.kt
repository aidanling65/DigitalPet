package com.example.tamagotchi.main.ui.components.dialogs.startup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.data_logging.google.GoogleScreen
import com.example.tamagotchi.data_logging.google.GoogleViewModel
import com.example.tamagotchi.data_logging.google.InteractionType
import com.example.tamagotchi.data_logging.google.LoadingState
import com.example.tamagotchi.intelligence.IntelligenceDifficulty
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay
import com.example.tamagotchi.minigames.GameDifficulty
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartupDialog(
    tamagotchiState: TamagotchiState,
    googleViewModel: GoogleViewModel,
    submitStepsGoal: (Int) -> Unit,
    updateBedTime: (LocalTime) -> Unit,
    updateWakeTime: (LocalTime) -> Unit,
    updatePuzzleDifficulty: (IntelligenceDifficulty) -> Unit,
    updateGameDifficulty: (GameDifficulty) -> Unit,
    onStartLoading: (InteractionType) -> Unit,
    onEndLoading: (LoadingState) -> Unit,
    onDismissRequest: () -> Unit,
) {
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

    var puzzleSliderPosition by remember { mutableFloatStateOf(tamagotchiState.puzzleDifficulty.ordinal.toFloat()) }
    var gameSliderPosition by remember { mutableFloatStateOf(tamagotchiState.gameDifficulty.ordinal.toFloat()) }

    Box(
        Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 8.dp, vertical = 16.dp)
        ) {
            Spacer(Modifier.height(16.dp))
            TamagotchiDisplay(tamagotchiState, modifier = Modifier.weight(0.2f))
            LazyColumn(Modifier.weight(0.6f)) {
                item {
                    GoogleScreen(googleViewModel, onStartLoading, onEndLoading)
                }

                item {
                    Text(
                        text = "Step goal",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    StepGoalField(
                        newStepGoal = newStepGoal,
                        onStepGoalChange = { newStepGoal = it },
                        onIncrement = { newStepGoal += 1000 },
                        onDecrement = { newStepGoal = (newStepGoal - 1000).coerceAtLeast(0) }
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.bed_time),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    TimeField(
                        bedTimeState,
                    )
                }
                item {
                    Text(
                        text = stringResource(R.string.wake_time),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    TimeField(
                        wakeTimeState,
                    )
                }

                item {
                    Text(
                        stringResource(R.string.puzzle_difficulty),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    val difficulties = IntelligenceDifficulty.entries
                    SliderCustom(
                        puzzleSliderPosition,
                        difficulties.size - 1,
                        0f..difficulties.size - 1f
                    ) { puzzleSliderPosition = it }
                }
                item {
                    Text(
                        stringResource(R.string.game_difficulty),
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                item {
                    val difficulties = GameDifficulty.entries
                    SliderCustom(
                        gameSliderPosition,
                        difficulties.size - 1,
                        0f..difficulties.size - 1f
                    ) { gameSliderPosition = it }
                }
            }
            SubmitButton(
                newStepGoal,
                Modifier.weight(0.1f),
                onDismissRequest,
            ) {
                submitStepsGoal(it)
                val bedTime = LocalTime.of(bedTimeState.hour, bedTimeState.minute)
                updateBedTime(bedTime)

                val wakeTime =
                    LocalTime.of(wakeTimeState.hour, wakeTimeState.minute)
                updateWakeTime(wakeTime)
                Log.d(
                    "StartupDialog",
                    "Difficulty: ${IntelligenceDifficulty.entries[puzzleSliderPosition.toInt()]}"
                )
                updatePuzzleDifficulty(IntelligenceDifficulty.entries[puzzleSliderPosition.toInt()])
                updateGameDifficulty(GameDifficulty.entries[gameSliderPosition.toInt()])
            }
        }
    }
}

/*@Preview(showBackground = true)
@Composable
fun StartupPreview() {
    StartupDialog(
        TamagotchiState(),
        {},
        {},
        {},
        {},
        {},
        {},
    )
}*/