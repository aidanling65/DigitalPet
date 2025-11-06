package com.example.tamagotchi.data.model

import com.example.tamagotchi.domain.logic.babyChildEvolve
import com.example.tamagotchi.domain.logic.childTeenEvolve
import com.example.tamagotchi.domain.logic.eggBabyEvolve
import com.example.tamagotchi.domain.logic.teenAdultEvolve
import java.time.Duration
import java.time.LocalTime

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
        stageLength = Duration.ofMinutes(65),
        //stageLength = Duration.ofSeconds(60),
        evolve = ::babyChildEvolve
    ),
    CHILD(
        minimumWeight = 10,
        bedTime = LocalTime.of(20, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(2),
        //stageLength = Duration.ofHours(24),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::childTeenEvolve
    ),
    TEEN(
        minimumWeight = 20,
        bedTime = LocalTime.of(21, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(2),
        //stageLength = Duration.ofHours(72),
        //stageLength = Duration.ofSeconds(10),
        evolve = ::teenAdultEvolve
    ),
    ADULT(
        minimumWeight = 30,
        bedTime = LocalTime.of(22, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = null,
        evolve = null
    ),
    DEAD(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = null,
        evolve = null
    );
}