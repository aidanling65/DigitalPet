package com.example.tamagotchi

data class TamagotchiState(
    val age: Int = 0,
    val weight: Int = 0,

    val hunger: Int = 0,
    val happiness: Int = 0,
    val discipline: Int = 0,

    val light: Boolean = true,

    val medicineTaken: Boolean = false,
    val sick: Boolean = false,

    val poop: Boolean = false,
    val misbehaving: Boolean = false,

    val animation: List<Int> = listOf<Int>()
)
