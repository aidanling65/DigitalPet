package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.dialogs.startup.StartupDialog
import com.example.tamagotchi.main.ui.components.manual.Manual
import com.example.tamagotchi.minigames.GameDialog
import com.example.tamagotchi.sudoku.ui.components.SudokuDialog

@Composable
fun Dialogs(gameViewModel: GameViewModel) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()

    val showStartup by gameViewModel.showStartup.collectAsState()
    val showReset by gameViewModel.showResetDialog.collectAsState()
    val showGame by gameViewModel.showGame.collectAsState()
    val showSudoku by gameViewModel.showSudoku.collectAsState()

    val startupDismiss = {
        if (tamagotchiState.initial) {
            gameViewModel.setupNewGame()
        } else {
            gameViewModel.onDismissStartup()
        }
    }
    Box(Modifier.fillMaxSize().background(Color.Transparent)) {
        DialogBase(
            showStartup, onDismissRequest = { gameViewModel.onDismissStartup() },
            DialogProperties(
                dismissOnBackPress = !tamagotchiState.initial,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            StartupDialog(
                gameViewModel,
                onDismissRequest = startupDismiss,
            )
        }

        DialogBase(
            tamagotchiState.hasEvolved,
            onDismissRequest = { gameViewModel.onDismissEvolution() }) {
            EvolutionDialog(
                tamagotchiState,
                onDismissRequest = { gameViewModel.onDismissEvolution() }
            )
        }
        DialogBase(showReset, onDismissRequest = { gameViewModel.onDismissResetDialog() }) {
            ResetDialog(
                onDismissRequest = { gameViewModel.onDismissResetDialog() },
                onConfirmation = { gameViewModel.confirmReset() }
            )
        }
        DialogBase(
            showGame, onDismissRequest = { gameViewModel.onDismissGame() },
            DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) {
            GameDialog(
                tamagotchiState,
                gameViewModel,
            )
        }
        DialogBase(showSudoku, onDismissRequest = { gameViewModel.onDismissSudoku() }) {
            SudokuDialog(
                viewModel = gameViewModel.sudokuViewModel,
                tamagotchiState = tamagotchiState,
                onGameWon = { gameViewModel.learning() }
            )
        }

        Manual(gameViewModel)
    }
}