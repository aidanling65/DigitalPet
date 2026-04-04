package com.example.digitalpet.main.ui.components.manual.contents

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
import com.example.digitalpet.R
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.ui.components.PetDisplay

@Composable
fun SleepContent() {
    Column(
        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.sleep_description),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                top = 4.dp,
                start = 8.dp,
                bottom = 8.dp
            ),
            color = Color.Black,
            lineHeight = 16.sp
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val sampleState = PetState(
                ageStage = AgeStage.ADULT,
                animations = EvolutionAnimations.ADULT_6,
                sleeping = true,
                light = true,
                loading = false
            )
            PetDisplay(
                sampleState,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal=16.dp)
                    .aspectRatio(1f)
                    .weight(1f)
            )
            val sampleState2 = PetState(
                ageStage = AgeStage.ADULT,
                animations = EvolutionAnimations.ADULT_6,
                sleeping = true,
                light = false,
                loading = false
            )
            PetDisplay(
                sampleState2,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 16.dp)
                    .aspectRatio(1f)
                    .weight(1f)
            )

        }
    }
}