package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository

class StepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository.getInstance(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            it.copy(
                resetSteps = true,
            )
        }
        return Result.success()
    }
}