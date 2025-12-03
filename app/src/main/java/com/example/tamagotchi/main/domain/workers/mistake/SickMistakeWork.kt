package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
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
        repository.updateState {
            if (it.sick) {
                if (Random.Default.nextFloat() < 0.1) {
                    death(applicationContext, it)
                } else {

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
        return Result.success()
    }
}