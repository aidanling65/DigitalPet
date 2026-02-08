package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.domain.workers.evolution.death
import java.time.Duration
import kotlin.random.Random

class SickMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var mistake = false

        repository.updateState {
            if(it.sleeping || it.paused){
                createSingleWorker<SickMistakeWork>(
                    applicationContext,
                    Duration.ofHours(1),
                    "sick_mistake",
                    ExistingWorkPolicy.REPLACE,
                )
                it
            }
            if (it.sick && !it.sleeping) {
                if (Random.Default.nextFloat() < 0.1) {
                    death(applicationContext, it)
                } else {
                    mistake = true
                    createSingleWorker<SickMistakeWork>(
                        applicationContext,
                        Duration.ofMinutes(30),
                        "sick_mistake",
                        ExistingWorkPolicy.REPLACE,
                    )
                    it.copy(
                        physicalMistakes = it.physicalMistakes + 1
                    )
                }
            }
            else{
                it
            }
        }

        if(mistake) {
            updateHistory(applicationContext) {
                it.copy(mistakesMade = it.mistakesMade + 1)
            }
        }
        return Result.success()
    }
}