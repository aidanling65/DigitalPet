package com.example.tamagotchi.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.showNotification
import java.time.Duration

class PoopSickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.sleeping){
            return Result.success()
        }

        if (state.poop && !state.sick) {
            showNotification(applicationContext, "Your tamagotchi is sick!")
            repository.updateState({currentState ->
                currentState.copy(
                    sick = true
                )
            })

            createSingleWorker<SickMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "sick_mistake",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}