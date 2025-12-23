package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.mistake.SickMistakeWork
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration
import kotlin.random.Random

class SickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val updatedState = repository.updateState{ current ->
            if(current.sleeping || current.sick){
                current
            }
            else if(Random.nextFloat() < 0.05f){
                current.copy(
                    sick = true
                )
            }
            else{
                current
            }

        }

        if(updatedState.sick && !updatedState.sleeping){
            attentionNotification(applicationContext, "Your Tamagotchi is sick!")

            createSingleWorker<SickMistakeWork>(
                applicationContext,
                Duration.ofMinutes(30),
                "sick",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}