package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import java.time.Duration

class MistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork() : Result{
        val statName = inputData.getString("statName") ?: return Result.failure()
        val interval = inputData.getLong("interval", 30)
        val mistakeType = inputData.getString("mistakeType") ?: "physical"

        val current = repository.getState()

        val statValue = when(statName){
            "hunger" -> current.hunger
            "happiness" -> current.happiness
            "poop" -> if(current.poop) 0 else 1
            "sleep" -> if(current.sleeping && current.light) 0 else 1
            "misbehaving" -> if(current.misbehaving) 0 else 1
            else -> 0
        }

        if(statValue == 0){
            val updated = when(mistakeType){
                "physical" ->
                    current.copy(
                        physicalMistakes = current.physicalMistakes + 1,
                        sick = if(statName == "poop") true else current.sick
                    )
                "mental" ->
                    current.copy(
                        mentalMistakes = current.mentalMistakes + 1,
                        misbehaving = if(statName == "misbehaving") false else current.misbehaving
                    )

                else -> current
            }

            repository.saveState(updated)

            createSingleWorker<MistakeWork>(
                applicationContext,
                Duration.ofMinutes(interval),
                statName,
                ExistingWorkPolicy.REPLACE,
                workDataOf(
                    "statName" to statName,
                    "interval" to interval,
                    "mistakeType" to mistakeType,
                )
            )
        }
        return Result.success()
    }
}