package com.example.tamagotchi.main.ui.components.manual.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
fun CleanContent() {
    Column(
        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp, start = 8.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.clean_description),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                top = 4.dp,
                start = 8.dp,
                bottom = 8.dp
            ),
            color = Color.Black,
            lineHeight = 16.sp
        )
        PetDisplay(
            PetState(
                ageStage = AgeStage.ADULT,
                animations = EvolutionAnimations.ADULT_3,
                poop = true,
                loading = false
            ),
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )
    }
}