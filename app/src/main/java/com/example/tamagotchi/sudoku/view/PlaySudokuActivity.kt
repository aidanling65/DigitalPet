package com.example.tamagotchi.sudoku.view

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.sudokuapp.viewmodel.PlaySudokuViewModel
import com.example.tamagotchi.databinding.ActivityPlaySudokuBinding
import com.example.tamagotchi.sudoku.game.Cell
import com.example.tamagotchi.sudoku.view.custom.SudokuBoardView

class PlaySudokuActivity : AppCompatActivity(), SudokuBoardView.OnTouchListener {

    private lateinit var binding: ActivityPlaySudokuBinding
    private lateinit var viewModel: PlaySudokuViewModel
    private lateinit var numberButtons: List<Button>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlaySudokuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.sudokuBoardView.registerListener(this)

        viewModel = ViewModelProvider(this)[PlaySudokuViewModel::class.java]
        viewModel.sudokuGame.selectedCellLiveData.observe(this) { updateSelectedCellUI(it) }
        viewModel.sudokuGame.cellsLiveData.observe(this) { updateCells(it) }
        viewModel.sudokuGame.isTakingNotesLiveData.observe(this) { updateNoteTakingUI(it) }
        viewModel.sudokuGame.highlightedKeysLiveData.observe(this) { updateHighlightedKeys(it) }

        numberButtons = listOf(binding.oneButton, binding.twoButton, binding.threeButton, binding.fourButton, binding.fiveButton, binding.sixButton,
                binding.sevenButton, binding.eightButton, binding.nineButton)

        numberButtons.forEachIndexed { index, button ->
            button.setOnClickListener { viewModel.sudokuGame.handleInput(index + 1) }
        }

        binding.notesButton.setOnClickListener { viewModel.sudokuGame.changeNoteTakingState() }
        binding.deleteButton.setOnClickListener { viewModel.sudokuGame.delete() }
    }

    private fun updateCells(cells: List<Cell>?) = cells?.let {
        binding.sudokuBoardView.updateCells(cells)
    }

    private fun updateSelectedCellUI(cell: Pair<Int, Int>?) = cell?.let {
        binding.sudokuBoardView.updateSelectedCellUI(cell.first, cell.second)
    }

    private fun updateNoteTakingUI(isNoteTaking: Boolean?) = isNoteTaking?.let {
        val color = if (it) ContextCompat.getColor(this, R.color.colorPrimary) else Color.LTGRAY
        binding.notesButton.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
    }



    private fun updateHighlightedKeys(set: Set<Int>?) = set?.let {
        numberButtons.forEachIndexed { index, button ->
            val color = if (set.contains(index + 1)) ContextCompat.getColor(this, R.color.colorPrimary) else Color.LTGRAY
            button.background.setColorFilter(color, PorterDuff.Mode.MULTIPLY)
        }
    }

    override fun onCellTouched(row: Int, col: Int) {
        viewModel.sudokuGame.updateSelectedCell(row, col)
    }
}
