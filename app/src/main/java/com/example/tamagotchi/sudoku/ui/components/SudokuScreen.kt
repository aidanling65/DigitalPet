package com.example.tamagotchi.sudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.components.bars.TopAppBar
import com.example.tamagotchi.main.ui.theme.TamagotchiTheme
import com.example.tamagotchi.sudoku.ui.SudokuBoardView
import com.example.tamagotchi.sudoku.ui.SudokuViewModel

@Composable
fun SudokuScreen(viewModel: SudokuViewModel, onCellTouched: (Int, Int) -> Unit) {
    val cells by viewModel.sudokuGame.cellsFlow.collectAsState()
    val selectedCell by viewModel.sudokuGame.selectedCellLiveData.observeAsState()

    val lineColor = MaterialTheme.colorScheme.secondary.toArgb()

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = { TopAppBar(null) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            // Use AndroidView to embed your existing SudokuBoardView
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .background(colorResource(R.color.white)), // Keep the board square
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

                    cells?.let { view.updateCells(it) }
                    selectedCell?.let { view.updateSelectedCellUI(it.first, it.second) }
                }
            )

            SudokuController(
                onNumberClick = { number -> viewModel.sudokuGame.handleInput(number) },
                onDeleteClick = { viewModel.sudokuGame.delete() },
                onNoteClick = { viewModel.sudokuGame.changeNoteTakingState() }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuScreenPreview() {
    TamagotchiTheme {
        SudokuScreen(
            viewModel = SudokuViewModel(),
            onCellTouched = { _, _ -> }
        )
    }

}
