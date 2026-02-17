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
import com.example.tamagotchi.main.data.data_logging.ActiveHistory
import com.example.tamagotchi.main.data.data_logging.PassiveHistory
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay
import com.example.tamagotchi.main.ui.components.VisualNoise
import com.example.tamagotchi.main.ui.components.manual.ManualEntry

@Composable
fun StatsDialog(
    tamagotchiState: TamagotchiState,
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
            TamagotchiDisplay(tamagotchiState, Modifier.weight(0.3f))
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
                    })
                }
                item {
                    ManualEntry("Nonograms", {
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
                    })
                }
                item {
                    ManualEntry("Sudoku", {
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
                    })
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
                                "Jumps played: ${activeHistory.timesFlappyPlayed}",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Black
                            )
                        }
                    })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsDialogPreview() {
    StatsDialog(TamagotchiState(), ActiveHistory(), PassiveHistory())
}