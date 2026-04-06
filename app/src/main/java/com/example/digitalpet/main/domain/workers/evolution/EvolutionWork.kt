package com.example.digitalpet.main.domain.workers.evolution

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.PetDatabase
import com.example.digitalpet.main.data.data_logging.PetHistoryRepository
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.domain.workers.utils.scheduleEssentialWorkers
import com.example.digitalpet.main.utils.showNotification
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit

class EvolutionWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = PetRepository(applicationContext)
    private val historyDb = PetDatabase.getDatabase(applicationContext)
    private val historyRepository = PetHistoryRepository(historyDb.historyDao())

    override suspend fun doWork(): Result {
        val currentTime = LocalTime.now(ZoneId.systemDefault())

        repository.updateState { state ->
            if (state.sleeping) {
                val wakeTime = state.wakeTime
                var hoursUntilWake = ChronoUnit.HOURS.between(currentTime, wakeTime)
                if (hoursUntilWake < 0) {
                    hoursUntilWake += 24
                }
                val durationUntilWake = Duration.ofHours(hoursUntilWake).plusMinutes(10)
                Log.d(
                    "EvolutionWork",
                    "Sleeping. Rescheduling evolution in ${durationUntilWake.seconds} seconds."
                )
                createSingleWorker<EvolutionWork>(
                    applicationContext,
                    durationUntilWake,
                    "evolve",
                    ExistingWorkPolicy.REPLACE
                )
                return@updateState state
            }

            val evolutionFunction = state.ageStage.evolve

            if (evolutionFunction != null) {
                if(state.ageStage == AgeStage.EGG){
                    showNotification(applicationContext, "Your egg has hatched!")
                } else{
                    showNotification(applicationContext, "Your pet has evolved!")
                }
                val (evolvedState, evolutionLog) = evolutionFunction(state)
                runBlocking {
                    historyRepository.storeEvolution(evolutionLog)
                }
                Log.d("EvolutionWork", evolvedState.animations.name)
                scheduleEssentialWorkers(applicationContext, evolvedState)
                evolvedState
            } else {
                state
            }
        }
        return Result.success()
    }
}
