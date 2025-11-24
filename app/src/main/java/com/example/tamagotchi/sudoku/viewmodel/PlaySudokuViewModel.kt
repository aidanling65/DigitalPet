package com.example.sudokuapp.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tamagotchi.sudoku.game.SudokuGame

class PlaySudokuViewModel : ViewModel() {
    val sudokuGame = SudokuGame()
}