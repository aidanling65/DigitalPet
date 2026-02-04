package com.example.tamagotchi.intelligence.sudoku.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun SudokuDialog(
    tamagotchiState: TamagotchiState,
    viewModel: SudokuViewModel,
    showWin: Boolean,
    onGameWon: () -> Unit,
    showWinScreen: ()->Unit,
) {
    val isGameWon by viewModel.sudokuGame.gameWonLiveData.observeAsState(initial = false)

    if (isGameWon) {
        showWinScreen()
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