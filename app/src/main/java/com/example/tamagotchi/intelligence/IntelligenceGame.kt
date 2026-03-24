package com.example.tamagotchi.intelligence

enum class IntelligenceGame {
    SUDOKU,
    NONOGRAM
}

enum class PuzzleDifficulty(val sudokuDigits: Int, val nonogramOdds: Float, val mistakes: Int){
    EASY(
        20,
        0.7f,
        8
    ),
    MEDIUM(
        30,
        0.6f,
        5
    ),
    Hard(
        40,
        0.5f,
        3
    ),
    EXTREME(
        50,
        0.4f,
        1
    )
}