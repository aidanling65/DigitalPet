package com.example.tamagotchi.main.data.model

const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4
const val MAX_DISCIPLINE = 4
const val MAX_FITNESS = 4
const val MAX_INTELLIGENCE = 4

data class TamagotchiState(

    val initial: Boolean = true,

    val age: Int = 0,

    val hunger: Int = 0,
    val happiness: Int = 0,

    val fitness: Int = 2,
    val stepGoal: Int = 10_000,
    val steps: Int = 0,
    val dailyStepBaseline: Int? = null,
    val resetSteps: Boolean = false,

    val discipline: Int = 0,
    val intelligence: Int = 0,

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
){
    val currentAnimation : List<Int>
        get() = when{
            sleeping && !light && animations.lightsOutSleep != null -> animations.lightsOutSleep
            sleeping && light && animations.sleep != null -> animations.sleep
            !sleeping && !light && animations.lightsOutAwake != null -> animations.lightsOutAwake
            else -> animations.idle
        }
}
