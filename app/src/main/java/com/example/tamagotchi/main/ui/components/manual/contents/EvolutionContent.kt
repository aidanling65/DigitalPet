package com.example.tamagotchi.main.ui.components.manual.contents

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.PetState
import com.example.tamagotchi.main.ui.components.PetDisplay

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
            color = Color.Black
        )
        Text(
            text = stringResource(R.string.evolution_explanation),
            style = MaterialTheme.typography.bodySmall,
            lineHeight = 16.sp,
            color = Color.Black
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val eggState = PetState(loading = false)
            PetDisplay(
                eggState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val babyState = PetState(
                ageStage = AgeStage.BABY,
                animations = EvolutionAnimations.BABY,
                loading = false
            )
            PetDisplay(
                babyState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val childState = PetState(
                ageStage = AgeStage.CHILD,
                animations = EvolutionAnimations.CHILD,
                loading = false
            )
            PetDisplay(
                childState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val teenState = PetState(
                ageStage = AgeStage.TEEN,
                animations = EvolutionAnimations.TEEN_2,
                loading = false
            )
            PetDisplay(
                teenState,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val adultStage = PetState(
                ageStage = AgeStage.ADULT,
                animations = EvolutionAnimations.ADULT_3,
                loading = false
            )
            PetDisplay(
                adultStage,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .weight(1f)
            )
        }
    }
}