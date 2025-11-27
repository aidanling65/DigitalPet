package com.example.tamagotchi.sudoku.game

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
        val grid = arrayOf(
            arrayOf(4, 7, 9, 6, 8, 5, 1, 3, 2),
            arrayOf(5, 3, 8, 2, 1, 9, 7, 6, 4),
            arrayOf(1, 6, 2, 7, 3, 4, 5, 9, 8),
            arrayOf(9, 1, 3, 5, 6, 8, 4, 2, 7),
            arrayOf(2, 5, 4, 1, 9, 7, 6, 8, 3),
            arrayOf(6, 8, 7, 3, 4, 2, 9, 1, 5),
            arrayOf(7, 2, 6, 8, 5, 1, 3, 4, 9),
            arrayOf(3, 4, 5, 9, 2, 6, 8, 7, 1),
            arrayOf(8, 9, 1, 4, 7, 3, 2, 5, 6)
        )
        val startingGrid = arrayOf(
            arrayOf(true, true, true, true, false, false, true, true, false),
            arrayOf(true, false, true, false, true, true, true, true, false),
            arrayOf(true, false, false, true, true, false, true, false, true),
            arrayOf(false, false, true, true, true, false, true, false, true),
            arrayOf(true, true, false, false, true, false, false, false, true),
            arrayOf(false, false, false, false, true, true, true, true, true),
            arrayOf(false, true, true, false, false, true, false, false, true),
            arrayOf(false, false, false, false, false, true, false, false, false),
            arrayOf(false, false, false, true, false, false, false, false, true),
        )

        correctCells = List(9 * 9) { i -> Cell(i / 9, i % 9, grid[i / 9][i % 9]) }
        val cells = List(9 * 9) { i ->
            val isStartingCell = startingGrid[i / 9][i % 9]
            Cell(
                i / 9,
                i % 9,
                if (isStartingCell) grid[i / 9][i % 9] else 0,
                isStartingCell = isStartingCell
            )
        }

        board = Board(9, cells)

        selectedCellLiveData.postValue(Pair(selectedRow, selectedCol))
        _cellsFlow.value = board.cells.toList()
        isTakingNotesLiveData.postValue(isTakingNotes)
        gameWonLiveData.postValue(false)
    }

    init {

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
        Log.d("SudokuGame", "handleInput called with number: $number with selected row $selectedRow column $selectedCol",)
        if (selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if (cell.isStartingCell) return

        val newCells = board.cells.toMutableList()
        val cellIndex = selectedRow * 9 + selectedCol
        val currentCell = newCells[cellIndex]

        if (isTakingNotes) {
            val newNotes= currentCell.notes.toMutableSet()
            if (newNotes.contains(number)) {
                newNotes.remove(number)
            } else {
                newNotes.add(number)
            }
            newCells[cellIndex] = currentCell.copy(notes=newNotes)
            highlightedKeysLiveData.postValue(cell.notes)
        } else {
            newCells[cellIndex] = currentCell.copy(value = number)
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
        if(selectedRow == -1 || selectedCol == -1) return
        val cell = board.getCell(selectedRow, selectedCol)
        if(cell.isStartingCell) return

        val newCells = board.cells.toMutableList()
        val cellIndex = selectedRow * 9 + selectedCol
        val currentCell = newCells[cellIndex]

        if (isTakingNotes) {
            newCells[cellIndex] = currentCell.copy(notes = mutableSetOf())
            highlightedKeysLiveData.postValue(setOf())
        } else {
            newCells[cellIndex] = currentCell.copy(value=0)
        }

        board = board.copy(cells=newCells)
        _cellsFlow.value = board.cells
    }
}
