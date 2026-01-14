package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun EvolutionContent() {
    Column(
        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(R.string.evolution_introduction),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.background
        )
        Text(
            text = stringResource(R.string.evolution_explanation),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = MaterialTheme.colorScheme.background
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val eggState = TamagotchiState()
            TamagotchiDisplay(
                eggState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val babyState = TamagotchiState(
                ageStage = AgeStage.BABY,
                animations = EvolutionAnimations.BABY
            )
            TamagotchiDisplay(
                babyState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val childState = TamagotchiState(
                ageStage = AgeStage.CHILD,
                animations = EvolutionAnimations.CHILD
            )
            TamagotchiDisplay(
                childState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val teenState = TamagotchiState(
                ageStage = AgeStage.TEEN,
                animations = EvolutionAnimations.TEEN_2
            )
            TamagotchiDisplay(
                teenState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val adultStage = TamagotchiState(
                ageStage = AgeStage.ADULT,
                animations = EvolutionAnimations.ADULT_3
            )
            TamagotchiDisplay(
                adultStage,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
        }
    }
}