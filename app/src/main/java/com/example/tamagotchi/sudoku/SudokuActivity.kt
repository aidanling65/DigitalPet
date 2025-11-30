package com.example.tamagotchi.sudoku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.tamagotchi.main.ui.theme.TamagotchiTheme
import com.example.tamagotchi.sudoku.ui.SudokuBoardView
import com.example.tamagotchi.sudoku.ui.components.SudokuScreen
import com.example.tamagotchi.sudoku.ui.SudokuViewModel

class SudokuActivity : ComponentActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: SudokuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[SudokuViewModel::class.java]
        viewModel.sudokuGame.gameWonLiveData.observe(this) { isWon ->
            if (isWon) {
                Toast.makeText(this, "Congratulations! You solved it!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        setContent {
            TamagotchiTheme {
                SudokuScreen(viewModel = viewModel, onCellTouched = this::onCellTouched)
            }
        }
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}