package com.example.tamagotchi.main.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.main.data.model.MAX_HAPPINESS
import com.example.tamagotchi.main.data.model.MAX_HUNGER
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun StatusBars(
    tamagotchiState: TamagotchiState,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        val spacerModifier = Modifier.height(16.dp)
        item{StatusBar(tamagotchiState.hunger, MAX_HUNGER, stringResource(R.string.hunger))}
        item{Spacer(modifier = spacerModifier)}
        item{StatusBar(tamagotchiState.discipline, MAX_DISCIPLINE, stringResource(R.string.discipline))}
        item{Spacer(modifier = spacerModifier)}
        item{StatusBar(tamagotchiState.happiness, MAX_HAPPINESS, stringResource(R.string.happiness))}
        item{Spacer(modifier = spacerModifier)}
        item{FitnessBar(tamagotchiState)}
        item{Spacer(modifier = spacerModifier)}
        item{Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                Text(
                    text = "State: ${tamagotchiState.ageStage.name}",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "${tamagotchiState.age} yr",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.misbehaving) stringResource(R.string.misbehaving) else stringResource(
                        R.string.well_behaved
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = stringResource(R.string.mistakes) + ": " + (tamagotchiState.mentalMistakes + tamagotchiState.physicalMistakes).toString(),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Column()
            {
                Text(
                    text = "${tamagotchiState.weight} Ib",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.poop) stringResource(R.string.dirty) else stringResource(
                        R.string.clean
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if(tamagotchiState.sick) stringResource(R.string.sick) else stringResource(
                        R.string.healthy
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }}
    }
}