package com.example.tamagotchi.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.tamagotchi.ui.GameViewModel

@Composable
fun TamagotchiApp(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    val showDialog by gameViewModel.showResetDialog.collectAsState()

    if (showDialog) {
        ResetDialog(
            onDismissRequest = { gameViewModel.onDismissDialog() },
            onConfirmation = { gameViewModel.confirmReset() }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = { TamagotchiAppBar(gameViewModel) },
        bottomBar = { BottomNavBar(gameViewModel) }
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
            TamagotchiDisplay(tamagotchiState)
            Spacer(Modifier.height(16.dp))
            StatusBars(tamagotchiState)
        }
    }
}