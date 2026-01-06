package com.example.tamagotchi.sudoku.domain

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class SudokuGame {

    var selectedCellLiveData = MutableLiveData<Pair<Int, Int>>()
    private val _cellsFlow = MutableStateFlow<List<Cell>?>(null)
    val cellsFlow = _cellsFlow.asStateFlow()

    val isTakingNotesLiveData = MutableLiveData<Boolean>()
    val highlightedKeysLiveData = MutableLiveData<Set<Int>>()
    val gameWonLiveData = MutableLiveData<Boolean>()

    private var selectedRow = -1
    private var selectedCol = -1
    private var isTakingNotes = false

    private lateinit var board: Board
    private lateinit var correctCells: List<Cell>

    fun fetchNewSudoku() {
        val generator = SudokuGenerator()
        val (fullSolution, startingMask) = generator.generate(40)

        correctCells = List(9 * 9) { i ->
            Cell(i / 9, i % 9, fullSolution[i / 9][i % 9])
        }
        val cells = List(9 * 9) { i ->
            val row = i / 9
            val col = i % 9
            val isStartingCell = startingMask[row][col]
            Cell(
                i / 9,
                i % 9,
                if (isStartingCell) fullSolution[row][col] else 0,
                isStartingCell = isStartingCell
            )
        }

        board = Board(9, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        _cellsFlow.value = board.cells.toList()
        isTakingNotesLiveData.postValue(isTakingNotes)
        gameWonLiveData.postValue(false)
    }

    private fun checkWin() {
        val isWon = board.cells.zip(correctCells).all { (current, correct) ->
            current.value == correct.value
        }

        if (isWon) {
            gameWonLiveData.postValue(true)
        }
    }

    fun handleInput(number: Int) {
        Log.d(
            "SudokuGame",
            "handleInput called with number: $number with selected row $selectedRow column $selectedCol",
        )
        if (selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if (cell.isStartingCell) return

        val newCells = board.cells.toMutableList()
        val cellIndex = selectedRow * 9 + selectedCol
        val currentCell = newCells[cellIndex]

        if (isTakingNotes) {
            val newNotes = currentCell.notes.toMutableSet()
            if (newNotes.contains(number)) {
                newNotes.remove(number)
            } else {
                newNotes.add(number)
            }
            newCells[cellIndex] = currentCell.copy(notes = newNotes)
            highlightedKeysLiveData.postValue(cell.notes)
        } else {
            newCells[cellIndex] = currentCell.copy(
                value = number,
                isCorrectOrEmpty = correctCells[cellIndex].value == number
            )
        }

        board = board.copy(cells = newCells)
        checkWin()
        _cellsFlow.value = board.cells
    }


    fun updateSelectedCell(row: Int, col: Int) {
        val cell = board.getCell(row, col)
        if (!cell.isStartingCell) {
            selectedRow = row
            selectedCol = col
            selectedCellLiveData.postValue(Pair(row, col))

            if (isTakingNotes) {
                highlightedKeysLiveData.postValue(cell.notes)
            }

        }
    }

    fun changeNoteTakingState() {
        isTakingNotes = !isTakingNotes
        isTakingNotesLiveData.postValue(isTakingNotes)

        val curNotes = if (isTakingNotes) {
            board.getCell(selectedRow, selectedCol).notes
        } else {
            setOf<Int>()
        }
        highlightedKeysLiveData.postValue(curNotes)
    }

    fun delete() {
        if (selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if (cell.isStartingCell) return

        val newCells = board.cells.toMutableList()
        val cellIndex = selectedRow * 9 + selectedCol
        val currentCell = newCells[cellIndex]

        if (isTakingNotes) {
            newCells[cellIndex] = currentCell.copy(notes = mutableSetOf())
            highlightedKeysLiveData.postValue(setOf())
        } else {
            newCells[cellIndex] = currentCell.copy(value = 0, isCorrectOrEmpty = true)
        }

        board = board.copy(cells = newCells)
        _cellsFlow.value = board.cells
    }
}
