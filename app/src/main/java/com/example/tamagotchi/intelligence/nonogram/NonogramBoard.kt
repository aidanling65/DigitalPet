package com.example.tamagotchi.intelligence.nonogram

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import kotlin.random.Random

data class NonogramBoard(
    val correctCells: List<List<Boolean>>,
    val playerCells: List<List<MutableState<Boolean>>>,
    val blockedCells: List<List<MutableState<Boolean>>>,
    val width: Int,
    val height: Int,
    var won: MutableState<Boolean> = mutableStateOf(false)
) {
    constructor(height:Int, width:Int):this(
        correctCells = List(height) { List(width) { Random.nextBoolean() } },
        playerCells = List(height) { List(width) { mutableStateOf(false) } },
        blockedCells = List(height) { List(width) { mutableStateOf(false) } },
        width = width,
        height = height,

    )

    private fun checkWon(){
        for(i in 0 until height){
            for(j in 0 until height){
                if(correctCells[i][j] != playerCells[i][j].value){
                    return
                }

            }
        }
        won.value = true
    }

    fun clickCell(row: Int, col: Int) {
        playerCells[row][col].value = !playerCells[row][col].value
        blockedCells[row][col].value = false

        checkWon()
    }

    fun blockCell(row: Int, col: Int) {
        blockedCells[row][col].value = !blockedCells[row][col].value
        playerCells[row][col].value = false
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

    fun getColumnHints(): List<List<Int>> {
        if (width == 0 || correctCells.isEmpty()) return emptyList()
        val hints: MutableList<List<Int>> = mutableListOf()
        for (i in 0 until width) {
            val column = correctCells.map { row ->
                    row[i]
            }
            val columnHints = hintsList(column)
            hints.add(columnHints)
        }
        return hints
    }

    fun getRowHints(): List<List<Int>> {
        if (height == 0 || correctCells.isEmpty()) return emptyList()
        val hints: MutableList<List<Int>> = mutableListOf()
        for (row in correctCells) {
            val rowHints = hintsList(row)
            hints.add(rowHints)
        }
        return hints
    }
}
