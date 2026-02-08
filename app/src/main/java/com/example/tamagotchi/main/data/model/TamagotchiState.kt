package com.example.tamagotchi.main.data.model

import com.example.tamagotchi.data_logging.LocalDateTimeSerializer
import com.example.tamagotchi.data_logging.LocalTimeSerializer
import com.example.tamagotchi.intelligence.IntelligenceDifficulty
import com.example.tamagotchi.minigames.GameDifficulty
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.LocalTime

const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4
const val MAX_DISCIPLINE = 4
const val MAX_FITNESS = 4
const val MAX_INTELLIGENCE = 4

@Serializable
data class TamagotchiState(

    val initial: Boolean = true,
    val loading: Boolean = true,

    val age: Int = 0,

    val hunger: Int = 0,
    val happiness: Int = 0,

    val fitness: Int = 2,
    val stepGoal: Int = 10_000,
    val steps: Int = 0,
    val stepGoalHit: Boolean = false,
    val stepGoal2Hit: Boolean = false,

    val discipline: Int = 0,
    val intelligence: Int = 0,

    val sleeping: Boolean = false,
    val lightAnimationState: Int = 0,
    val light: Boolean = true,

    val medicineTaken: Boolean = false,
    val sick: Boolean = false,

    val poop: Boolean = false,
    val misbehaving: Boolean = false,

    val physicalMistakes: Int = 0,
    val mentalMistakes: Int = 0,
    val mistakes: Int = 0,

    @Serializable(with= LocalTimeSerializer::class)
    val wakeTime: LocalTime = LocalTime.of(9,0),

    @Serializable(with= LocalTimeSerializer::class)
    val bedTime: LocalTime = LocalTime.of(22,0),

    val ageStage: AgeStage = AgeStage.EGG,
    val animations: EvolutionAnimations = EvolutionAnimations.EGG,

    val weight: Int = ageStage.minimumWeight,

    val hasEvolved: Boolean = false,

    @Serializable(with= LocalDateTimeSerializer::class)
    val lastEvolve: LocalDateTime = LocalDateTime.now(),

    val puzzleDifficulty: IntelligenceDifficulty = IntelligenceDifficulty.EASY,
    val gameDifficulty: GameDifficulty = GameDifficulty.EASY,

    val paused: Boolean = false,
){
    val currentAnimation : Int
        get() = when{
            sick -> animations.sick ?: animations.idle
            sleeping && !light -> animations.lightsOutSleep
            sleeping && animations.sleep != null -> animations.sleep
            !sleeping && !light -> animations.lightsOutAwake
            else -> animations.idle
        }
}
