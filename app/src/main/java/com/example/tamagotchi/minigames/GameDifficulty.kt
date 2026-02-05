package com.example.tamagotchi.minigames

enum class GameDifficulty(val flappyVelocity: Float, val obstacleInitialMinVelocity: Float, val obstacleInitialMaxVelocity: Float, val obstacleAcceleration: Float) {
    EASY(
        -7f,
        -13f,
        -13f,
        0.1f
    ),
    MEDIUM(
-10f,
        -13f,
        -15f,
        0.15f
    ),
    HARD(
-13f,
        -15f,
        -17f,
        0.2f
    ),
    EXTREME(
-16f,
        -17f,
        -20f,
        0.25f
    )
}