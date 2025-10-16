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
    val stageLength: Duration?
) {
    EGG(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = Duration.ofMinutes(5)
    ),
    BABY(
        minimumWeight = 5,
        bedTime = null,
        wakeTime = null,
        stageLength = Duration.ofMinutes(65)
    ),
    CHILD(
        minimumWeight = 10,
        bedTime = LocalTime.of(20, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(24)
    ),
    TEEN(
        minimumWeight = 20,
        bedTime = LocalTime.of(21, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(72)
    ),
    ADULT(
        minimumWeight = 30,
        bedTime = LocalTime.of(22, 0),
        wakeTime = LocalTime.of(9, 0),
        stageLength = Duration.ofHours(72)
    ),
    DEAD(
        minimumWeight = 0,
        bedTime = null,
        wakeTime = null,
        stageLength = null
    );
}

