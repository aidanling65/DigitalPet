package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.domain.workers.mistakes.FitnessMistakeWork
import java.time.Duration

class FitnessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()

        state = state.copy(
            fitness = state.fitness - 1
        )
        repository.saveState(state)

        if(state.fitness == 0){
            createSingleWorker<FitnessMistakeWork>(
                applicationContext,
                Duration.ofHours(24),
                "fitness_mistake",
                ExistingWorkPolicy.REPLACE,
            )
        }

        return Result.success()
    }
}