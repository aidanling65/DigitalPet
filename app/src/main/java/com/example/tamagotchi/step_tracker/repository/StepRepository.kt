package com.example.tamagotchi.step_tracker.repository

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class StepRepository(
    private val stepsDao: StepsDao
) {
    suspend fun storeSteps(stepsSinceLastReboot: Long) = withContext(Dispatchers.IO) {
        val stepCount = StepCount(
            steps=stepsSinceLastReboot,
            createdAt=Instant.now().toString()
        )
        Log.d("StepCount", "Storing steps: $stepCount")
        stepsDao.insertAll(stepCount)
    }

    fun loadTodaysSteps(): Flow<Long> {
        val todayAtMidnight = LocalDateTime.of(LocalDate.now(ZoneId.systemDefault()), LocalTime.MIDNIGHT).toString()
        return stepsDao.loadAllStepsFromToday(startDateTime = todayAtMidnight).map { todayDataPoints ->
            when {
                todayDataPoints.isEmpty() -> 0
                else -> {
                    val firstDataPointOfTheDay = todayDataPoints.first()
                    val latestDataPointSoFar = todayDataPoints.last()

                    val todaySteps = latestDataPointSoFar.steps - firstDataPointOfTheDay.steps
                    Log.d("StepCount", "Today's steps: $todaySteps")
                    todaySteps
                }
            }
        }
    }
}