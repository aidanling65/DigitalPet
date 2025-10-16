package com.example.tamagotchi

import android.os.Build
import androidx.annotation.RequiresApi
const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4
const val MAX_DISCIPLINE = 5

data class TamagotchiState @RequiresApi(Build.VERSION_CODES.O) constructor(
    val age: Int = 0,

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

    val ageStage: AgeStage = AgeStage.EGG,
    val animations: EvolutionAnimations = EvolutionAnimations.EGG,

    val weight: Int = ageStage.minimumWeight,
)
