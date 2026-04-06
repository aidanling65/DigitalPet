package com.example.digitalpet.main.ui.components.manual

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.digitalpet.R
import com.example.digitalpet.main.ui.components.manual.contents.CleanContent
import com.example.digitalpet.main.ui.components.manual.contents.DeathContent
import com.example.digitalpet.main.ui.components.manual.contents.EvolutionContent
import com.example.digitalpet.main.ui.components.manual.contents.HappinessContent
import com.example.digitalpet.main.ui.components.manual.contents.HungerContent
import com.example.digitalpet.main.ui.components.manual.contents.MistakeContent
import com.example.digitalpet.main.ui.components.manual.contents.SickContent
import com.example.digitalpet.main.ui.components.manual.contents.SleepContent

@Composable
fun ManualColumn() {
    LazyColumn(
        Modifier.clip(RoundedCornerShape(10.dp)),
        horizontalAlignment = Alignment.Start
    ) {
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            ManualEntry(
                title = stringResource(R.string.mistakes),
                painterId = R.drawable.mistakes,
                content = {
                    MistakeContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.evolution),
                painterId = R.drawable.chromosome,
                content = {
                    EvolutionContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.death),
                painterId = R.drawable.skull,
                content = {
                    DeathContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.happiness),
                painterId = R.drawable.play,
                content = {
                    HappinessContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.hunger),
                painterId = R.drawable.knife_fork,
                content = {
                    HungerContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.discipline),
                painterId = R.drawable.discipline,
                content = {
                    Text(
                        text = stringResource(R.string.discipline_description),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        ),
                        color = Color.Black,
                        lineHeight = 16.sp
                    )
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.intelligence),
                painterId = R.drawable.intelligence,
                content = {
                    Text(
                        text = stringResource(R.string.intelligence_description),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        ),
                        color = Color.Black,
                        lineHeight = 16.sp
                    )
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.sleep),
                painterId = R.drawable.light_bulb,
                content = {
                    SleepContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.cleanliness),
                painterId = R.drawable.clean,
                content = {
                    CleanContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.sickness),
                painterId = R.drawable.heal,
                content = {
                    SickContent()
                }
            )
        }
        item {
            ManualEntry(
                title = stringResource(R.string.fitness),
                painterId = R.drawable.dumpbell,
                content = {
                    Text(
                        text = stringResource(R.string.fitness_description),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(
                            top = 4.dp,
                            start = 8.dp,
                            bottom = 8.dp
                        ),
                        color = Color.Black,
                        lineHeight = 16.sp
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualColumnPreview() {
    ManualColumn()
}