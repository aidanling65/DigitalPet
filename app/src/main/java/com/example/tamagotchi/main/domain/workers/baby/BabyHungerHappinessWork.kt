package com.example.tamagotchi.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration

class BabyHungerHappinessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentState = repository.getState()

        if (currentState.ageStage != AgeStage.BABY) {
            return Result.success()
        }

        if (!currentState.sleeping) {
            repository.updateState { currentState ->
                val updatedState = currentState.copy(
                    hunger = if (currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                    happiness = if (currentState.happiness > 0) currentState.happiness - 1 else currentState.happiness
                )
                if (updatedState.hunger == 0) {
                    attentionNotification(applicationContext, "You Tamagotchi is hungry!")
                }
                if (updatedState.happiness == 0) {
                    attentionNotification(applicationContext, "You Tamagotchi is sad!")
                }
                updatedState
            }
        }
        createSingleWorker<BabyHungerHappinessWork>(
            applicationContext,
            Duration.ofMinutes(3),
            "hunger_happiness",
            ExistingWorkPolicy.REPLACE,
        )

        return Result.success()
    }
}