package com.example.tamagotchi

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4
const val MAX_DISCIPLINE = 5

data class TamagotchiState(
    val age: Int = 0,

    val hunger: Int = 0,
    val happiness: Int = 0,
    val discipline: Int = 0,

    val sleeping: Boolean = false,
    val light: Boolean = true,

    val medicineTaken: Boolean = false,
    val sick: Boolean = false,

    val poop: Boolean = true,
    val misbehaving: Boolean = false,

    val physicalMistakes: Int = 0,
    val mentalMistakes: Int = 0,

    val ageStage: AgeStage = AgeStage.EGG,
    val animations: EvolutionAnimations = EvolutionAnimations.EGG,

    val weight: Int = ageStage.minimumWeight,
){
    val currentAnimation : List<Int>
        get() = when{
            sleeping && !light && animations.lights_out_sleep != null -> animations.lights_out_sleep
            sleeping && light && animations.sleep != null -> animations.sleep
            !sleeping && !light && animations.lights_out_awake != null -> animations.lights_out_awake
            else -> animations.idle
        }
}
