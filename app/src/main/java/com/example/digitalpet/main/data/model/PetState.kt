package com.example.digitalpet.main.data.model

import com.example.digitalpet.R
import com.example.digitalpet.main.data.data_logging.LocalDateTimeSerializer
import com.example.digitalpet.main.data.data_logging.LocalTimeSerializer
import com.example.digitalpet.intelligence.PuzzleDifficulty
import com.example.digitalpet.minigames.GameDifficulty
import com.example.digitalpet.theme.PetColor
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
data class PetState(

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

    val puzzleDifficulty: PuzzleDifficulty = PuzzleDifficulty.EASY,
    val gameDifficulty: GameDifficulty = GameDifficulty.EASY,

    val paused: Boolean = false,

    val color : PetColor = PetColor.LCD,
){
    val currentAnimation : Int
        get() = when{
            !light-> {
                if (sleeping) R.drawable.lights_out_sleep else R.drawable.lights_out_awake
            }
            sick -> animations.sick ?: animations.idle
            sleeping && animations.sleep != null -> animations.sleep
            else -> animations.idle
        }
}
