package com.example.tamagotchi.intelligence.sudoku.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.tamagotchi.intelligence.sudoku.domain.SudokuGame
import kotlinx.coroutines.launch

class SudokuViewModelFactory(private val missingDigits: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SudokuViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SudokuViewModel(missingDigits) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class SudokuViewModel(val missingDigits:Int) : ViewModel() {
    val sudokuGame = SudokuGame()

    init{
        viewModelScope.launch {
            sudokuGame.fetchNewSudoku(missingDigits)
        }
    }
}