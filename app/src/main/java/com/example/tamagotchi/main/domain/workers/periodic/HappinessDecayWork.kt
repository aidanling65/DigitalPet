package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.mistake.HappinessMistakeWork
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration

class HappinessDecayWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {

        val updatedState = repository.updateState { current ->
            if (current.sleeping) {
                current
            } else {
                current.copy(
                    happiness = if (current.happiness > 0) current.happiness - 1 else current.happiness,
                )
            }
        }

        if (updatedState.happiness == 0 && !updatedState.sleeping) {
            attentionNotification(applicationContext, "Your Tamagotchi is sad!")
            createSingleWorker<HappinessMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "happiness_mistake",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}