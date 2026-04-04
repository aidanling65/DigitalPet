package com.example.digitalpet.intelligence.sudoku.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalpet.intelligence.PuzzleLossDialog
import com.example.digitalpet.intelligence.PuzzleWinDialog
import com.example.digitalpet.intelligence.sudoku.domain.SudokuGame
import com.example.digitalpet.intelligence.sudoku.ui.SudokuViewModel
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.ui.components.VisualNoise

@Composable
fun SudokuDialog(
    petState: PetState,
    viewModel: SudokuViewModel,
    showWin: Boolean,
    showLoss: Boolean,
    onWin: () -> Unit,
    onLoss: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    val isGameWon by viewModel.sudokuGame.gameWonLiveData.observeAsState(initial = false)
    val gameMistakes by viewModel.sudokuGame.mistakes.observeAsState(initial = 0)

    var finishedGame by remember { mutableStateOf<SudokuGame?>(null) }
    val solvedGame by remember(finishedGame){
        mutableStateOf(
            finishedGame?.copy()?.apply {
                makeCorrect()
            }
        )
    }

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            finishedGame = viewModel.sudokuGame.copy()
            onWin()
        }
    }
    if (showWin) {
        PuzzleWinDialog(
            true,
            "Congratulations!\nYou solved the Sudoku!",
            petState,
            { onDismissRequest() }
        ) {
            val solvedViewModel = remember(solvedGame) {
                SudokuViewModel(viewModel.missingDigits).apply {
                    this.sudokuGame = solvedGame!!
                }
            }
            SudokuGrid(solvedViewModel) { v1, v2 -> {} }
        }
    }

    LaunchedEffect(gameMistakes) {
        if (gameMistakes >= petState.puzzleDifficulty.mistakes) {
            finishedGame = viewModel.sudokuGame.copy()
            onLoss()
        }
    }
    if (showLoss) {
        PuzzleLossDialog(
            true,
            "Too bad\nYou failed the Sudoku!",
            petState,
            { onDismissRequest() }
        ) {
            val solvedViewModel = remember(solvedGame) {
                SudokuViewModel(viewModel.missingDigits).apply {
                    this.sudokuGame = solvedGame!!
                }
            }
            SudokuGrid(solvedViewModel) { v1, v2 -> {} }
        }
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f)
    ) {
        VisualNoise()
        SudokuScreen(
            viewModel = viewModel,
            petState = petState,
            onCellTouched = { row, col -> viewModel.sudokuGame.updateSelectedCell(row, col) }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuPreview() {
    val sudokuViewModel: SudokuViewModel = viewModel()
    SudokuDialog(
        petState = PetState(),
        viewModel = sudokuViewModel,
        showWin = true,
        showLoss = false,
        onWin = {},
        onLoss = {}) {}
}