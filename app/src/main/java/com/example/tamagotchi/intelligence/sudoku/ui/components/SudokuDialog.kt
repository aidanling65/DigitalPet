package com.example.tamagotchi.intelligence.sudoku.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.GameViewModel

@Composable
fun SudokuDialog(
    tamagotchiState: TamagotchiState,
    gameViewModel: GameViewModel,
    onGameWon: () -> Unit
) {
    val viewModel = gameViewModel.sudokuViewModel
    val isGameWon by viewModel.sudokuGame.gameWonLiveData.observeAsState(initial = false)
    val showWin by gameViewModel.showWinScreen.collectAsState()

    if (isGameWon) {
        gameViewModel.showWinScreen()
        PuzzleWinDialog(
            showWin,
            "Congratulations!\nYou solved the Sudoku!",
            tamagotchiState
        ) {
            onGameWon()
        }
    }
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f)
    ) {
        SudokuScreen(
            viewModel = viewModel,
            tamagotchiState = tamagotchiState,
            onCellTouched = { row, col -> viewModel.sudokuGame.updateSelectedCell(row, col) }
        )
    }
}