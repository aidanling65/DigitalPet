package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.manual.ManualDrawer
import com.example.tamagotchi.minigames.GameDialog
import com.example.tamagotchi.sudoku.ui.components.SudokuDialog

@Composable
fun Dialogs(gameViewModel: GameViewModel) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()

    val showStartup by gameViewModel.showStartup.collectAsState()
    val showReset by gameViewModel.showResetDialog.collectAsState()
    val showGame by gameViewModel.showGame.collectAsState()
    val showSudoku by gameViewModel.showSudoku.collectAsState()
    val showManual by gameViewModel.showManual.collectAsState()

    if (showStartup) {
        StartupDialog(
            tamagotchiState,
            onDismissRequest = {
                if (tamagotchiState.initial) gameViewModel.setupNewGame()
                else gameViewModel.onDismissStartup()
            },
            submitSteps = { gameViewModel.submitStepsGoal(it) },
            incrementStepGoal = { gameViewModel.incrementStepsGoal() },
            decrementStepGoal = { gameViewModel.decrementStepsGoal() },
            backPressDismiss = if(tamagotchiState.initial) false else true
        )
    }

    if (tamagotchiState.hasEvolved) {
        EvolutionDialog(
            tamagotchiState,
            onDismissRequest = { gameViewModel.onDismissEvolution() }
        )
    }
    if (showReset) {
        ResetDialog(
            onDismissRequest = { gameViewModel.onDismissResetDialog() },
            onConfirmation = { gameViewModel.confirmReset() }
        )
    }
    if (showGame) {
        GameDialog(
            tamagotchiState,
            gameViewModel,
            onDismissRequest = { gameViewModel.onDismissGame() },
            Modifier.fillMaxWidth(0.95f)
        )
    }
    if (showSudoku) {
        SudokuDialog(
            viewModel = gameViewModel.sudokuViewModel,
            tamagotchiState = tamagotchiState,
            onDismissRequest = { gameViewModel.onDismissSudoku() },
            onGameWon = { gameViewModel.learning() }
        )
    }
    if (showManual) {
        ManualDrawer(onDismissRequest = { gameViewModel.onDismissManual() })
    }
}