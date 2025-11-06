package com.example.tamagotchi.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.data.model.TamagotchiState
import kotlinx.coroutines.runBlocking
import java.time.Duration

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override fun doWork(): Result{
        var updatedState: TamagotchiState? = null
        runBlocking {
            val currentState = repository.getState()
            updatedState = currentState.ageStage.evolve?.let { it(currentState) }
            Log.d("EvolutionWork", updatedState?.animations?.name ?: "")
            repository.saveState(updatedState ?: currentState)
        }

        if(updatedState?.ageStage == AgeStage.DEAD){
            return Result.success()
        }
        val delay = updatedState?.ageStage?.stageLength ?: Duration.ZERO
        if(delay == Duration.ZERO){
            return Result.success()
        }

        createSingleWorker<EvolutionWork>(delay,"evolve", ExistingWorkPolicy.REPLACE)

        Log.d("EvolutionWork", "next evolution scheduled for in ${delay.seconds}")

        return Result.success()
    }
}