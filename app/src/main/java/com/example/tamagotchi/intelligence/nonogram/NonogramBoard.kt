package com.example.tamagotchi.intelligence.nonogram

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

data class NonogramBoard(
    val width: Int,
    val height: Int,
    var fillProbability: Float = 0.5f,
) {
    var correctCells: List<List<Boolean>> = fetchNewNonogram(fillProbability)
    val playerCells: List<List<MutableState<Boolean>>> = List(height) { List(width) { mutableStateOf(false) } }
    val blockedCells: List<List<MutableState<Boolean>>> = List(height) { List(width) { mutableStateOf(false) } }
    val rowHints: List<List<Int>>
    val columnHints: List<List<Int>>
    var won: MutableState<Boolean> = mutableStateOf(false)
    var mistakes: MutableState<Int> = mutableIntStateOf(0)

    init{
        rowHints = calculateRowHints()
        columnHints = calculateColumnHints()
    }

    private fun fetchNewNonogram(fillProbability: Float = 0.5f): List<List<Boolean>>{
        this.fillProbability = fillProbability
        val board = MutableList(height){ MutableList(width){Random.nextFloat() < fillProbability} }

        for(i in 0 until height){
            if (board[i].all { !it }){
                board[i][Random.nextInt(width)] = true
                board[i][Random.nextInt(width)] = true
            }
        }
        for(i in 0 until width){
            if(board.all { !it[i] }){
                board[Random.nextInt(height)][i] = true
                board[Random.nextInt(height)][i] = true
            }
        }

        return board
    }

    private fun checkWon(){
        for(i in 0 until height){
            for(j in 0 until width){
                if(correctCells[i][j] != playerCells[i][j].value){
                    return
                }

            }
        }
        won.value = true
    }

    suspend fun undoMove(row: Int, col: Int){
        Log.d("Nonogram", "Undoing move")
        delay(500)
        blockedCells[row][col].value = true
        playerCells[row][col].value = false
        checkWon()
    }

    fun clickCell(row: Int, col: Int) {
        if(!blockedCells[row][col].value) {
            playerCells[row][col].value = !playerCells[row][col].value

            if (playerCells[row][col].value && !correctCells[row][col]) {
                mistakes.value++
                CoroutineScope(Dispatchers.Default).launch {
                    undoMove(row, col)
                }
            }
            rowCorrect(row)
            colCorrect(col)
        }

        checkWon()
    }

    fun blockCell(row: Int, col: Int) {
        blockedCells[row][col].value = !blockedCells[row][col].value
        playerCells[row][col].value = false
    }

    private fun getColumn(colIndex: Int, grid: List<List<Boolean>>): List<Boolean>{
        return grid.map { row ->
            row[colIndex]
        }
    }

    private fun hintsList(list: List<Boolean>): List<Int> {
        val hints: MutableList<Int> = mutableListOf()
        var currentLength = 0
        for (i in list) {
            if (i) {
                currentLength++
            } else if (currentLength > 0) {
                hints.add(currentLength)
                currentLength = 0
            }
        }
        if(currentLength > 0) hints.add(currentLength)
        return hints
    }

    private fun calculateColumnHints(): List<List<Int>> {
        if (width == 0 || correctCells.isEmpty()) return emptyList()
        val hints: MutableList<List<Int>> = mutableListOf()
        for (i in 0 until width) {
            val column = getColumn(i, correctCells)
            val columnHints = hintsList(column)
            hints.add(columnHints)
        }
        return hints
    }

    private fun calculateRowHints(): List<List<Int>> {
        if (height == 0 || correctCells.isEmpty()) return emptyList()
        val hints: MutableList<List<Int>> = mutableListOf()
        for (row in correctCells) {
            val rowHints = hintsList(row)
            hints.add(rowHints)
        }
        return hints
    }


    private fun rowCorrect(rowIndex: Int){
        val playerRow = playerCells[rowIndex].map { it.value }
        if(correctCells[rowIndex] == playerRow){
            for( j in 0 until width) {
                if (!correctCells[rowIndex][j]) {
                    blockedCells[rowIndex][j].value = true
                }
            }
        }
    }

    private fun colCorrect(colIndex: Int){
        val correctColumn = getColumn(colIndex, correctCells)
        val playerColumn = playerCells.map{row -> row[colIndex].value}

        if(correctColumn == playerColumn){
            for( i in 0 until height) {
                if (!correctColumn[i]) {
                    blockedCells[i][colIndex].value = true
                }
            }
        }
    }

}
