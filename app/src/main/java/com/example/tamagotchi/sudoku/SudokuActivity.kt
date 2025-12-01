package com.example.tamagotchi.sudoku

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.tamagotchi.main.data.model.MAX_INTELLIGENCE
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.ui.theme.TamagotchiTheme
import com.example.tamagotchi.sudoku.ui.SudokuBoardView
import com.example.tamagotchi.sudoku.ui.components.SudokuScreen
import com.example.tamagotchi.sudoku.ui.SudokuViewModel
import kotlinx.coroutines.launch

class SudokuActivity : ComponentActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var viewModel: SudokuViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = TamagotchiRepository.getInstance(applicationContext)
        viewModel = ViewModelProvider(this)[SudokuViewModel::class.java]
        viewModel.sudokuGame.gameWonLiveData.observe(this) { isWon ->
            if (isWon) {
                Toast.makeText(this, "Congratulations! You solved it!", Toast.LENGTH_SHORT).show()
                lifecycleScope.launch {
                    repository.updateState {
                        it.copy(
                            intelligence = (it.intelligence + 1).coerceAtMost(MAX_INTELLIGENCE)
                        )
                    }
                }
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