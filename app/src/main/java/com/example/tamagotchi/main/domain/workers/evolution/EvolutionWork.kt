package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.utils.scheduleEssentialWorkers
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(applicationContext)

    override suspend fun doWork(): Result{
        val currentState = repository.getState()
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        if(currentState.sleeping){
            val wakeTime = currentState.ageStage.wakeTime
            if(wakeTime != null){
                val durationUntilWake = if (currentTime.isBefore(wakeTime)) {
                    Duration.between(currentTime, wakeTime)
                } else {
                    Duration.between(currentTime, LocalTime.MAX)
                        .plus(Duration.between(LocalTime.MIN, wakeTime))
                }.plus(Duration.ofMinutes(10))

                Log.d("EvolutionWork", "Sleeping. Rescheduling evolution in ${durationUntilWake.seconds} seconds.")
                createSingleWorker<EvolutionWork>(
                    applicationContext,
                    durationUntilWake,
                    "evolve",
                    ExistingWorkPolicy.REPLACE
                )
                return Result.success()
            }
        }
        val evolutionFunction = currentState.ageStage.evolve

        if(evolutionFunction != null) {
            val updatedState = evolutionFunction(applicationContext, currentState)
            Log.d("EvolutionWork", updatedState.animations.name)
            repository.saveState(updatedState)

            scheduleEssentialWorkers(applicationContext, updatedState)
        }

        return Result.success()
    }
}