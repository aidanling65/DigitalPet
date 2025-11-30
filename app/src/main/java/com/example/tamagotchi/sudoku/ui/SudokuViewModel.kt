package com.example.tamagotchi.sudoku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamagotchi.sudoku.domain.SudokuGame
import kotlinx.coroutines.launch

class SudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()

    init{
        viewModelScope.launch {
            sudokuGame.fetchNewSudoku()
        }
    }
}