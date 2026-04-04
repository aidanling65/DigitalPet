package com.example.digitalpet.main.data.model

import android.content.Context
import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.domain.workers.evolution.babyChildEvolve
import com.example.digitalpet.main.domain.workers.evolution.childTeenEvolve
import com.example.digitalpet.main.domain.workers.evolution.eggBabyEvolve
import com.example.digitalpet.main.domain.workers.evolution.teenAdultEvolve
import kotlinx.serialization.Serializable
import java.time.Duration

@Serializable
enum class AgeStage(
    val minimumWeight: Int,
    val stageLength: Duration?,
    val evolve: ((Context, PetState, Boolean) -> Pair<PetState, EvolutionLog>)?,
    val misbehaviorChances: Float,
    val mistakesLimit: Int?
) {
    EGG(
        minimumWeight = 0,
        stageLength = Duration.ofSeconds(10),
        evolve = ::eggBabyEvolve,
        misbehaviorChances = 0f,
        mistakesLimit = null,
    ),
    BABY(
        minimumWeight = 5,
        stageLength = Duration.ofMinutes(65),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::babyChildEvolve,
        misbehaviorChances = 0f,
        mistakesLimit = null
    ),
    CHILD(
        minimumWeight = 10,
        stageLength = Duration.ofHours(24),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::childTeenEvolve,
        misbehaviorChances = 0.125f,
        mistakesLimit = 5

    ),
    TEEN(
        minimumWeight = 20,
        stageLength = Duration.ofHours(72),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::teenAdultEvolve,
        misbehaviorChances = 0.175f,
        mistakesLimit = 10
    ),
    ADULT(
        minimumWeight = 30,
        stageLength = null,
        evolve = null,
        misbehaviorChances = 0.03f,
        mistakesLimit = 15
    ),
    DEAD(
        minimumWeight = 0,
        stageLength = null,
        evolve = null,
        misbehaviorChances = 0f,
        mistakesLimit = null
    );
}