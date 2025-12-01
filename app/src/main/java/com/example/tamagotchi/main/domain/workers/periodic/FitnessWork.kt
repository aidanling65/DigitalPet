package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.domain.workers.mistake.FitnessMistakeWork
import java.time.Duration

class FitnessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository.getInstance(appContext)

    override suspend fun doWork(): Result {

        val updatedState = repository.updateState { current ->
            current.copy(
                fitness = current.fitness - 1,
                weight =  if(current.fitness == MAX_FITNESS && current.weight > current.ageStage.minimumWeight) current.weight - 1 else current.weight
            )
        }

        if(updatedState.fitness == 0){
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