package com.example.tamagotchi.minigames

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.GameViewModel
import kotlin.random.Random

@Composable
fun GameDialog(
    tamagotchiState: TamagotchiState,
    gameViewModel: GameViewModel,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f)
            .clip(RoundedCornerShape(10))
            .background(MaterialTheme.colorScheme.background)
    ) {
        when (Random.nextInt(0, 2)) {
            0 -> JumpGameCanvas(tamagotchiState, gameViewModel)
            1 -> FlappyGameCanvas(tamagotchiState, gameViewModel)
        }
    }
}