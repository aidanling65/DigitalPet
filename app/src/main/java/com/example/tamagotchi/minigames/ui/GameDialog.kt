package com.example.tamagotchi.minigames.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.VisualNoise
import com.example.tamagotchi.minigames.FlappyGameCanvas
import com.example.tamagotchi.minigames.JumpGameCanvas
import com.example.tamagotchi.minigames.Minigames
import kotlin.random.Random

@Composable
fun GameDialog(
    tamagotchiState: TamagotchiState,
    gameScore: (Int, Minigames) -> Unit
) {

    val randomGame = remember { mutableIntStateOf(Random.nextInt(0, 2)) }

    fun restart() {
        randomGame.intValue = Random.nextInt(0, 2)
    }
    Box {
        VisualNoise(
            Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f)
                .clip(RoundedCornerShape(10))
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.95f)
                .clip(RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.background)
        ) {
            when (randomGame.intValue) {
                0 -> JumpGameCanvas(
                    tamagotchiState,
                    { it -> gameScore(it, Minigames.JUMP) },
                    { restart() })

                1 -> FlappyGameCanvas(
                    tamagotchiState,
                    { it -> gameScore(it, Minigames.FLAPPY) },
                    { restart() })
            }
        }
    }
}