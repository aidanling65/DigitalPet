package com.example.tamagotchi.sudoku.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.tamagotchi.main.ui.theme.TamagotchiTheme
import com.example.tamagotchi.sudoku.ui.SudokuScreen
import com.example.tamagotchi.sudoku.view.custom.SudokuBoardView
import com.example.tamagotchi.sudoku.viewmodel.PlaySudokuViewModel

class PlaySudokuActivity : ComponentActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: PlaySudokuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this)[PlaySudokuViewModel::class.java]
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