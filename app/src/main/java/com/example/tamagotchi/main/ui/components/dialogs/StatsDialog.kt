package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.data_logging.ActiveHistory
import com.example.tamagotchi.main.data.data_logging.PassiveHistory
import com.example.tamagotchi.main.data.model.PetState
import com.example.tamagotchi.main.ui.components.PetDisplay
import com.example.tamagotchi.main.ui.components.VisualNoise
import com.example.tamagotchi.main.ui.components.manual.ManualEntry
import com.example.tamagotchi.theme.AppTheme
import com.example.tamagotchi.theme.DigitalPetTheme

@Composable
fun StatsDialog(
    petState: PetState,
    activeHistory: ActiveHistory,
    passiveHistory: PassiveHistory
) {
    Box(
        Modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f)
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        VisualNoise()
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .weight(0.075f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Stats",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            PetDisplay(petState, Modifier.weight(0.3f))
            Spacer(Modifier.weight(0.025f))
            LazyColumn(
                Modifier
                    .weight(0.6f)
                    .padding(horizontal = 16.dp)
            ) {
                item {
                    ManualEntry("Care", {
                        Column(Modifier.padding(start = 32.dp)) {
                            Text(
                                "Times fed: ${activeHistory.timesFed}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Poops: ${passiveHistory.timesPooped}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Times Sick: ${passiveHistory.timesSick}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Times Disciplined: ${activeHistory.timesDisciplined}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Times Misbehaved: ${passiveHistory.timesMisbehaved}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Times slept: ${passiveHistory.timesSlept}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Deaths: ${passiveHistory.deaths}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    },
                        R.drawable.heal
                    )
                }
                item {
                    ManualEntry(
                        "Nonograms", {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Solved: ${activeHistory.nonogramsSolved}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black
                                )
                                Text(
                                    "Failed: ${activeHistory.nonogramsFailed}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black
                                )
                            }
                        },
                        R.drawable.nonogram_icon
                    )
                }
                item {
                    ManualEntry(
                        "Sudoku",
                        {
                            Row(
                                Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Text(
                                    "Solved: ${activeHistory.sudokusSolved}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black
                                )
                                Text(
                                    "Failed: ${activeHistory.sudokusFailed}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Black
                                )
                            }
                        },
                        R.drawable.sudoku_icon
                    )
                }
                item {
                    ManualEntry("Minigames", {
                        //Text("Minigames", style = MaterialTheme.typography.bodyMedium, color = Color.Black)
                        Column(Modifier.padding(start = 32.dp)) {
                            Text(
                                "Jumps played: ${activeHistory.timesJumpPlayed}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                            Text(
                                "Flappys played: ${activeHistory.timesFlappyPlayed}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    },
                        R.drawable.play)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsDialogPreview() {
    DigitalPetTheme(AppTheme.PINK) {
        StatsDialog(PetState(), ActiveHistory(), PassiveHistory())
    }
}