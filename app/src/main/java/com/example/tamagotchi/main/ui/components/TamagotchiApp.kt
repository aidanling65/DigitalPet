package com.example.tamagotchi.main.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.bars.BottomBar
import com.example.tamagotchi.main.ui.components.bars.TopAppBar
import com.example.tamagotchi.main.ui.components.dialogs.Dialogs
import com.example.tamagotchi.main.ui.components.status_bars.StatusBars

@Composable
fun TamagotchiApp(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    val showEating by gameViewModel.showEatingAnimation.collectAsState()
    val showManual by gameViewModel.showManual.collectAsState()

    Box(modifier=Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background),
            topBar = {
                TopAppBar(
                    tamagotchiState,
                    showManual,
                    { gameViewModel.onManualClicked() },
                    { gameViewModel.onStartupOpen() },
                    { gameViewModel.pauseGame() },
                    { gameViewModel.onResetClicked() },
                    { gameViewModel.onStatsClicked() }
                )
            },
            bottomBar = {
                BottomBar(
                    { gameViewModel.feed() },
                    { gameViewModel.light() },
                    { gameViewModel.clean() },
                    { gameViewModel.heal() },
                    { gameViewModel.play() },
                    { gameViewModel.onLaunchIntelligence() },
                    { gameViewModel.discipline() }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background)
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Spacer(Modifier.height(16.dp))
                TamagotchiDisplay(
                    tamagotchiState,
                    modifier.fillMaxWidth(0.65f),
                    { gameViewModel.onEatingAnimationFinished() },
                    showEating
                )
                Spacer(Modifier.height(16.dp))
                StatusBars(tamagotchiState)
                Spacer(Modifier.height(16.dp))
            }

        }
        Dialogs(gameViewModel)
        VisualNoise()
    }
}