package com.example.tamagotchi.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.showNotification
import java.time.Duration

class BabySleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var currentState = repository.getState()
        var updatedState = currentState.copy(
            sleeping = true
        )
        repository.saveState(updatedState)
        showNotification(applicationContext, "You Tamagotchi is sleeping")

        createSingleWorker<BabyWakeWork>(
            applicationContext,
            Duration.ofMinutes(5),
            "wake",
            ExistingWorkPolicy.REPLACE
        )

        return Result.success()
    }
}