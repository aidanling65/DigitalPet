package com.example.tamagotchi.intelligence.sudoku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamagotchi.intelligence.sudoku.domain.SudokuGame
import kotlinx.coroutines.launch

class SudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()

    init{
        viewModelScope.launch {
            sudokuGame.fetchNewSudoku()
        }
    }
}