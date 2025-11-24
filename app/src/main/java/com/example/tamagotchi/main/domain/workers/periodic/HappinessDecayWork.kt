package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.mistake.HappinessMistakeWork
import com.example.tamagotchi.main.utils.showNotification
import java.time.Duration

class HappinessDecayWork(
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
            happiness =  if(state.happiness > 0) state.happiness - 1 else state.happiness,
        )

        if(updatedState.happiness == 0){
            showNotification(applicationContext, "Your Tamagotchi is sad!")
            createSingleWorker<HappinessMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "happiness_mistake",
                ExistingWorkPolicy.REPLACE
            )
        }

        repository.saveState(updatedState)

        return Result.success()
    }
}