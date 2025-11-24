package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.mistake.SickMistakeWork
import com.example.tamagotchi.main.utils.showNotification
import kotlin.random.Random
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

        if(Random.nextFloat() < 0.05f && !state.sick){
            showNotification(applicationContext, "Your Tamagotchi is sick!")
            state = state.copy(
                sick = true
            )
            repository.saveState(state)

            createSingleWorker<SickMistakeWork>(
                applicationContext,
                Duration.ofMinutes(30),
                "sick",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}