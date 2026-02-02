package com.example.tamagotchi.intelligence.sudoku.domain

data class Cell(
    val row: Int,
    val col: Int,
    var value: Int,
    var isStartingCell: Boolean = false,
    var isCorrectOrEmpty: Boolean = true,
    var notes: MutableSet<Int> = mutableSetOf<Int>()
)