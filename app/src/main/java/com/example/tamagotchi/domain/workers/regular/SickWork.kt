package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import java.time.Duration

class SickWork(
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
            state = state.copy(sick = true)
            repository.saveState(state)

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