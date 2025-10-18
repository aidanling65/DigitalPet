package com.example.tamagotchi

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import java.time.Duration

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result{
        var currentState: TamagotchiState? = null
        runBlocking {
            currentState = repository.getState()
            val updatedState = currentState.ageStage.evolve?.let { it(currentState) }
            Log.d("EvolutionWork", updatedState?.animations?.name ?: "")
            repository.saveState(updatedState?: currentState)
        }

        if(currentState?.ageStage == AgeStage.DEAD){
            return Result.success()
        }

        val delay = currentState?.ageStage?.stageLength ?: Duration.ZERO
        val evolutionRequest = OneTimeWorkRequestBuilder<EvolutionWork>()
            .setInitialDelay(delay)
            .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniqueWork(
            "evolve",
            ExistingWorkPolicy.REPLACE,
            evolutionRequest
        )

        Log.d("EvolutionWork", "next evolution scheduled for in ${delay.seconds}")

        return Result.success()
    }
}