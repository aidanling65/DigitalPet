package com.example.tamagotchi.intelligence.sudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tamagotchi.R
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuBoardView
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModelFactory

@Composable
fun SudokuGrid(viewModel: SudokuViewModel, onCellTouched: (Int, Int) -> Unit) {
    val cells by viewModel.sudokuGame.cellsFlow.collectAsState()
    val selectedCell by viewModel.sudokuGame.selectedCellLiveData.observeAsState()
    val lineColor = MaterialTheme.colorScheme.secondary.toArgb()
    val selectedColor = MaterialTheme.colorScheme.background.copy(alpha=0.75f).toArgb()
    val sameValueColor = MaterialTheme.colorScheme.background.copy(alpha=0.5f).toArgb()

    AndroidView(
        modifier = Modifier
            .shadow(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(colorResource(R.color.white)),
        factory = { context ->
            SudokuBoardView(context, null).apply {
                registerListener(object : SudokuBoardView.OnTouchListener {
                    override fun onCellTouched(row: Int, col: Int) {
                        onCellTouched(row, col)
                    }
                })
            }
        },
        update = { view ->
            view.setLineColor(lineColor)
            view.setSelectedColor(selectedColor)
            view.setSameValueColor(sameValueColor)

            cells?.let { view.updateCells(it) }
            selectedCell?.let { view.updateSelectedCellUI(it.first, it.second) }
        }
    )
}

@Preview
@Composable
fun SudokuGridPreview() {
    val sudokuViewModel: SudokuViewModel = viewModel(
        factory = SudokuViewModelFactory(20)
    )
    SudokuGrid(
        sudokuViewModel
    ) { v1,v2 -> {}}
}