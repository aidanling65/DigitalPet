package com.example.tamagotchi.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.evolution.death
import java.time.Duration
import kotlin.random.Random

class SickMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.sick) {
            if(Random.Default.nextFloat() < 0.1)
            {
                state = death(applicationContext,state)
            }
            else
            {
                state = state.copy(
                    physicalMistakes = state.physicalMistakes + 1
                )

                createSingleWorker<SickMistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(30),
                    "sick_mistake",
                    ExistingWorkPolicy.REPLACE,
                )
            }
            repository.saveState(state)
        }
        return Result.success()
    }
}