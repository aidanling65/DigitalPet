package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.zIndex
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.ConfettiSource
import com.example.tamagotchi.main.ui.ConfettiView
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun EvolutionDialog(
    tamagotchiState: TamagotchiState,
    onDismissRequest: () -> Unit,
) {
    val ageStage = tamagotchiState.ageStage
    Box(
        Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.weight(0.1f))
            Text(
                text = when (ageStage) {
                    AgeStage.BABY -> stringResource(R.string.hatched_dialog)
                    AgeStage.DEAD -> stringResource(R.string.death_dialog)
                    else -> stringResource(R.string.evolved_dialog)
                },
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xffffffff),
                textAlign = TextAlign.Center
            )
            TamagotchiDisplay(
                tamagotchiState,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
                    .weight(1f)
            )
            IconButton(
                onClick = onDismissRequest,
                content = {
                    Row(
                        Modifier
                            .clip(RoundedCornerShape(10))
                            .background(Color(0xFF396C39))
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .weight(0.2f),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Dismiss"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(0.75f)
            )
            Spacer(modifier = Modifier.weight(0.1f))
        }
        ConfettiView(
            source = ConfettiSource.TOP,
            quantity = 100,
            modifier = Modifier.zIndex(1f)
        )
    }
}