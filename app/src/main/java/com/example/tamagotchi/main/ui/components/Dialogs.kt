package com.example.tamagotchi.main.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.minigame.GameDialog
import com.example.tamagotchi.sudoku.ui.components.SudokuDialog

@Composable
fun Dialogs(gameViewModel: GameViewModel){
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()

    val showDialog by gameViewModel.showResetDialog.collectAsState()
    val showGame by gameViewModel.showGame.collectAsState()
    val showSudoku by gameViewModel.showSudoku.collectAsState()

    if (showDialog) {
        ResetDialog(
            onDismissRequest = { gameViewModel.onDismissDialog() },
            onConfirmation = { gameViewModel.confirmReset() }
        )
    }
    if(showGame){
        GameDialog(
            tamagotchiState,
            onDismissRequest = {gameViewModel.onDismissGame()},
            Modifier.fillMaxSize()
        )
    }
    if(showSudoku){
        SudokuDialog(
            viewModel = gameViewModel.sudokuViewModel,
            tamagotchiState = tamagotchiState,
            onDismissRequest = { gameViewModel.onDismissSudoku() },
            onGameWon = {
                gameViewModel.learning()
            }
        )
    }
}