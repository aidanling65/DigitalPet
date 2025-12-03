package com.example.tamagotchi.step_tracker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.step_tracker.repository.StepDatabase
import com.example.tamagotchi.step_tracker.repository.StepRepository

class StepCounterWorker(
    appContext: Context,
    workerParams: WorkerParameters,
): CoroutineWorker(appContext, workerParams)
{
    private val db = StepDatabase.getDatabase(applicationContext)

    private val stepRepository = StepRepository(db.stepsDao())
    private val stepCounter = StepCounter(appContext)

    override suspend fun doWork(): Result {
        val stepsSinceLastReboot = stepCounter.steps()
        if(stepsSinceLastReboot == 0L) return Result.success()

        stepRepository.storeSteps(stepsSinceLastReboot)

        return  Result.success()
    }
}