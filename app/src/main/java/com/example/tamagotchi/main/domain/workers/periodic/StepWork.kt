package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository

class StepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        val updatedState = state.copy(
            resetSteps = true,
        )
        repository.saveState(updatedState)
        return Result.success()
    }
}