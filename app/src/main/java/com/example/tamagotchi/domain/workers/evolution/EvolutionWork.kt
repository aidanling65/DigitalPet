package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result{
        val currentState = repository.getState()
        val updatedState = currentState.ageStage.evolve?.let { it(currentState) }
        Log.d("EvolutionWork", updatedState?.animations?.name ?: "")
        repository.saveState(updatedState ?: currentState)

        return Result.success()
    }
}