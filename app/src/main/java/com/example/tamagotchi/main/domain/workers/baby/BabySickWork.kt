package com.example.tamagotchi.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.utils.showNotification

class BabySickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams){
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentState = repository.getState()
        val updatedState = currentState.copy(
            sick = true
        )
        showNotification(applicationContext, "You Tamagotchi is sick!")
        repository.saveState(updatedState)

        return Result.success()
    }
}
