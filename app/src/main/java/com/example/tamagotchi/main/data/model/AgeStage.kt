package com.example.tamagotchi.main.data.model

import android.content.Context
import com.example.tamagotchi.domain.workers.evolution.babyChildEvolve
import com.example.tamagotchi.domain.workers.evolution.childTeenEvolve
import com.example.tamagotchi.domain.workers.evolution.eggBabyEvolve
import com.example.tamagotchi.domain.workers.evolution.teenAdultEvolve
import java.time.Duration
import java.time.LocalTime

enum class AgeStage(
    val minimumWeight: Int,
    val bedTime: LocalTime?,
    val wakeTime: LocalTime?,
    val stageLength: Duration?,
    val evolve: ((Context, TamagotchiState) -> TamagotchiState)?,
    val misbehaviorChances: Float,
) {
    EGG(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = Duration.ofMinutes(5),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::eggBabyEvolve,
        misbehaviorChances = 0f,
    ),
    BABY(
        minimumWeight = 5,
        bedTime = null,
        wakeTime = null,
        stageLength = Duration.ofMinutes(65),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::babyChildEvolve,
        misbehaviorChances = 0f,
    ),
    CHILD(
        minimumWeight = 10,
        bedTime = LocalTime.of(20, 0),
        wakeTime = LocalTime.of(9, 0),
        //stageLength = Duration.ofHours(1),
        stageLength = Duration.ofHours(24),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::childTeenEvolve,
        misbehaviorChances = 0.125f

    ),
    TEEN(
        minimumWeight = 20,
        bedTime = LocalTime.of(21, 0),
        wakeTime = LocalTime.of(9, 0),
        //stageLength = Duration.ofHours(2),
        stageLength = Duration.ofHours(72),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::teenAdultEvolve,
        misbehaviorChances = 0.175f
    ),
    ADULT(
        minimumWeight = 30,
        bedTime = LocalTime.of(22, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = null,
        evolve = null,
        misbehaviorChances = 0.03f
    ),
    DEAD(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = null,
        evolve = null,
        misbehaviorChances = 0f
    );
}