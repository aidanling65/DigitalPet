package com.example.tamagotchi.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification

class BabyPoopWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentState = repository.getState()
        val updatedState = currentState.copy(
            poop = true
        )
        repository.saveState(updatedState)
        showNotification(applicationContext, "You Tamagotchi has pooped!")

        return Result.success()
    }
}