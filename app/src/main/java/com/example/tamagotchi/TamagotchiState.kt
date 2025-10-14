package com.example.tamagotchi

import java.time.LocalTime

const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4
const val MAX_DISCIPLINE = 5

data class TamagotchiState(
    val age: Int = 0,
    val weight: Int = 0,

    val hunger: Int = 0,
    val happiness: Int = 0,
    val discipline: Int = 0,

    val sleeping: Boolean = false,
    val light: Boolean = true,

    val medicineTaken: Boolean = false,
    val sick: Boolean = false,

    val poop: Boolean = false,
    val misbehaving: Boolean = false,

    val physicalMistakes: Int = 0,
    val mentalMistakes: Int = 0,
    val animation: List<Int> = listOf<Int>()
)
