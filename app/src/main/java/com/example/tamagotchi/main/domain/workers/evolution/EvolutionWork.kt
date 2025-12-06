package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.domain.workers.utils.scheduleEssentialWorkers
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

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
                var hoursUntilWake = ChronoUnit.HOURS.between(currentTime,wakeTime)
                if(hoursUntilWake < 0){
                    hoursUntilWake += 24
                }
                val durationUntilWake = Duration.ofHours(hoursUntilWake).plusMinutes(10)
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

        repository.updateState { state ->
            val evolutionFunction = state.ageStage.evolve

            if (evolutionFunction != null) {
                val updatedState = evolutionFunction(applicationContext, state)
                Log.d("EvolutionWork", updatedState.animations.name)
                scheduleEssentialWorkers(applicationContext, updatedState)
                updatedState
            } else {
                state
            }
        }

        return Result.success()
    }
}
