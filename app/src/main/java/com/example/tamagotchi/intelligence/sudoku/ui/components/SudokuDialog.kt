package com.example.tamagotchi.intelligence.sudoku.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tamagotchi.intelligence.PuzzleLossDialog
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun SudokuDialog(
    tamagotchiState: TamagotchiState,
    viewModel: SudokuViewModel,
    showWin: Boolean,
    showLoss: Boolean,
    onWin: () -> Unit,
    onLoss: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val isGameWon by viewModel.sudokuGame.gameWonLiveData.observeAsState(initial = false)
    val gameMistakes by viewModel.sudokuGame.mistakes.observeAsState(initial = 0)

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            onWin()
        }
    }
    if (showWin) {
        PuzzleWinDialog(
            true,
            "Congratulations!\nYou solved the Sudoku!",
            tamagotchiState
        ) {
            onDismissRequest()
        }
    }

    LaunchedEffect(gameMistakes) {
        if (gameMistakes >= tamagotchiState.difficulty.mistakes) {
            onLoss()
        }
    }
    if (showLoss) {
        PuzzleLossDialog(
            true,
            "Too bad\nYou failed the Sudoku!",
            tamagotchiState
        ) { onDismissRequest() }
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

@Preview(showBackground = true)
@Composable
fun SudokuPreview() {
    val sudokuViewModel: SudokuViewModel = viewModel()
    SudokuDialog(
        tamagotchiState = TamagotchiState(),
        viewModel = sudokuViewModel,
        showWin = true,
        showLoss = false,
        onWin = {},
        onLoss = {}){}
}