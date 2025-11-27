package com.example.tamagotchi.sudoku.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamagotchi.sudoku.game.SudokuGame
import kotlinx.coroutines.launch

class PlaySudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()

    init{
        viewModelScope.launch {
            sudokuGame.fetchNewSudoku()
        }
    }
}