package com.example.tamagotchi.main.data.model

import android.content.Context
import com.example.tamagotchi.main.domain.workers.evolution.babyChildEvolve
import com.example.tamagotchi.main.domain.workers.evolution.childTeenEvolve
import com.example.tamagotchi.main.domain.workers.evolution.eggBabyEvolve
import com.example.tamagotchi.main.domain.workers.evolution.teenAdultEvolve
import java.time.Duration

enum class AgeStage(
    val minimumWeight: Int,
    val stageLength: Duration?,
    val evolve: ((Context, TamagotchiState) -> TamagotchiState)?,
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
        evolve = ::babyChildEvolve,
        misbehaviorChances = 0f,
        mistakesLimit = null
    ),
    CHILD(
        minimumWeight = 10,
        //stageLength = Duration.ofHours(24),
        stageLength = Duration.ofSeconds(10),
        evolve = ::childTeenEvolve,
        misbehaviorChances = 0.125f,
        mistakesLimit = 5

    ),
    TEEN(
        minimumWeight = 20,
        stageLength = Duration.ofHours(72),
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