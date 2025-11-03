package com.example.tamagotchi

import java.time.LocalTime
import java.time.Duration

enum class AgeStage(
    val minimumWeight: Int,
    val bedTime: LocalTime?,
    val wakeTime: LocalTime?,
    val stageLength: Duration?,
    val evolve: ((TamagotchiState) -> TamagotchiState)?
) {
    EGG(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        //stageLength = Duration.ofMinutes(5),
        stageLength = Duration.ofSeconds(10),
        evolve = ::eggBabyEvolve
    ),
    BABY(
        minimumWeight = 5,
        bedTime = null,
        wakeTime = null,
        //stageLength = Duration.ofMinutes(65),
        stageLength = Duration.ofSeconds(10),
        evolve = ::babyChildEvolve
    ),
    CHILD(
        minimumWeight = 10,
        bedTime = LocalTime.of(20, 0),
        wakeTime = LocalTime.of(9, 0),
        //stageLength = Duration.ofHours(24),
        stageLength = Duration.ofSeconds(10),
        evolve = ::childTeenEvolve
    ),
    TEEN(
        minimumWeight = 20,
        bedTime = LocalTime.of(21, 0),
        wakeTime = LocalTime.of(9, 0),
        //stageLength = Duration.ofHours(72),
        stageLength = Duration.ofSeconds(10),
        evolve = ::teenAdultEvolve
    ),
    ADULT(
        minimumWeight = 30,
        bedTime = LocalTime.of(22, 0),
        wakeTime = LocalTime.of(9, 0),
        //stageLength = Duration.ofHours(72),
        stageLength = Duration.ofSeconds(10),
        evolve = ::deadEvolve
    ),
    DEAD(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = null,
        evolve = null
    );
}

