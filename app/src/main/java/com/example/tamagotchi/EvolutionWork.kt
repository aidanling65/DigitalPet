package com.example.tamagotchi

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result{
        runBlocking {
            val currentState = repository.getState()
            val updatedState = currentState.ageStage.evolve?.let { it(currentState) }

            repository.saveState(updatedState?: currentState)
        }
        return Result.success()
    }
}