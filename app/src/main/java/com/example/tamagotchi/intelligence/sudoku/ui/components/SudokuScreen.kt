package com.example.tamagotchi.intelligence.sudoku.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.intelligence.MistakeMeter
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun SudokuScreen(
    viewModel: SudokuViewModel,
    tamagotchiState: TamagotchiState,
    onCellTouched: (Int, Int) -> Unit
) {
    val mistakes by viewModel.sudokuGame.mistakes.observeAsState(initial = 5)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Tamagoku",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.weight(0.05f))
            TamagotchiDisplay(
                tamagotchiState, modifier = Modifier
                    .fillMaxWidth(0.4f)
                    .aspectRatio(1f),
            )
            MistakeMeter(mistakes, tamagotchiState.puzzleDifficulty.mistakes)
            Spacer(Modifier.weight(0.05f))
            SudokuGrid(viewModel, onCellTouched)
            Spacer(Modifier.weight(0.05f))
            SudokuController(
                onNumberClick = { number -> viewModel.sudokuGame.handleInput(number) },
                onDeleteClick = { viewModel.sudokuGame.delete() },
                onNoteClick = { viewModel.sudokuGame.changeNoteTakingState() }
            )
            Spacer(Modifier.weight(0.05f))
        }
    }
}
