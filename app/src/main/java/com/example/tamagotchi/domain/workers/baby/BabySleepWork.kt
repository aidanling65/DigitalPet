package com.example.tamagotchi.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification
import kotlinx.coroutines.delay

class BabySleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)
    private val delay: Long = 300_000

    override suspend fun doWork(): Result {
        var currentState = repository.getState()
        var updatedState = currentState.copy(
            sleeping = true
        )
        repository.saveState(updatedState)
        showNotification(applicationContext, "You Tamagotchi is sleeping")

        delay(delay)
        currentState = repository.getState()
        updatedState = currentState.copy(
            sleeping = false,
            age = currentState.age + 1,
            light = true,
        )
        repository.saveState(updatedState)

        return Result.success()
    }
}