package com.example.tamagotchi

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalTime
import java.time.Duration

@RequiresApi(Build.VERSION_CODES.O)
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
        stageLength = Duration.ofMinutes(5),
        evolve = ::eggBabyEvolve
    ),
    BABY(
        minimumWeight = 5,
        bedTime = null,
        wakeTime = null,
        stageLength = Duration.ofMinutes(65),
        evolve = ::babyChildEvolve
    ),
    CHILD(
        minimumWeight = 10,
        bedTime = LocalTime.of(20, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(24),
        evolve = ::childTeenEvolve
    ),
    TEEN(
        minimumWeight = 20,
        bedTime = LocalTime.of(21, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(72),
        evolve = ::teenAdultEvolve
    ),
    ADULT(
        minimumWeight = 30,
        bedTime = LocalTime.of(22, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(72),
        evolve = ::adultDeadEvolve
    ),
    DEAD(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = null,
        evolve = null
    );
}

