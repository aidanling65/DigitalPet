package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.domain.workers.mistakes.HappinessMistakeWork
import com.example.tamagotchi.domain.workers.mistakes.HungerMistakeWork
import com.example.tamagotchi.utils.showNotification
import java.time.Duration

class HungerDecayWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.sleeping){
            return Result.success()
        }

        val updatedState = state.copy(
            hunger = if(state.hunger > 0) state.hunger - 1 else state.hunger,
        )

        if(updatedState.hunger == 0){
            showNotification(applicationContext, "You Tamagotchi is hungry!")
            createSingleWorker<HungerMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "hunger_mistake",
                ExistingWorkPolicy.REPLACE
            )
        }

        repository.saveState(updatedState)

        return Result.success()
    }
}