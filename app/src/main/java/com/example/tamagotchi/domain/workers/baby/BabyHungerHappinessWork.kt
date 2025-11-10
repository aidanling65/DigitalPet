package com.example.tamagotchi.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.utils.showNotification
import java.time.Duration

class BabyHungerHappinessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentState = repository.getState()

        if(currentState.ageStage != AgeStage.BABY){
            return Result.success()
        }

        if (!currentState.sleeping) {
            val updatedState = currentState.copy(
                hunger = if (currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                happiness = if (currentState.happiness > 0) currentState.happiness - 1 else currentState.happiness
            )
            if (updatedState.hunger == 0) {
                showNotification(applicationContext, "You Tamagotchi is hungry!")
            }
            if (updatedState.happiness == 0) {
                showNotification(applicationContext, "You Tamagotchi is sad!")
            }
            repository.saveState(updatedState)
        }
        createSingleWorker<BabyHungerHappinessWork>(
            applicationContext,
            Duration.ofMinutes(30),
            "hunger_happiness",
            ExistingWorkPolicy.REPLACE,
        )

        return Result.success()
    }
}