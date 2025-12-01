package com.example.tamagotchi.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.utils.showNotification

class BabyPoopWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository.getInstance(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            val updatedState = it.copy(
                poop = true
            )
            updatedState
        }
        showNotification(applicationContext, "You Tamagotchi has pooped!")

        return Result.success()
    }
}