package com.example.tamagotchi.domain.workers.mistakes

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
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
                state = death(state)
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