package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.mistake.FitnessMistakeWork
import java.time.Duration

class FitnessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()

        state = state.copy(
            fitness = state.fitness - 1,
            weight =  if(state.fitness == MAX_FITNESS && state.weight > state.ageStage.minimumWeight) state.weight - 1 else state.weight
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