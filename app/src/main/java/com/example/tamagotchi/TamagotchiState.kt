package com.example.tamagotchi

data class TamagotchiState(
    val age: Byte = 0,
    val weight: Byte = 0,

    val hunger: Byte = 0,
    val happiness: Byte = 0,
    val discipline: Byte = 0,

    var medicineTaken: Boolean = false,
    val sick: Boolean = false,

    val poop: Boolean = false,
    val misbehaving: Boolean = false,

    val animation: List<Int> = listOf<Int>()
)
