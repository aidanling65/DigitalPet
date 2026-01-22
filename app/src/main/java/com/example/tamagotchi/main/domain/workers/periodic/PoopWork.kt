package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.mistake.PoopSickWork
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration
import kotlin.random.Random

class PoopWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {

        val updatedState = repository.updateState { current->
            if(current.sleeping || current.poop || current.paused) {
                current
            }
            else if(Random.nextFloat() < 0.5f){
                current.copy(
                    poop = true
                )
            }
            else{
                current
            }
        }

        if(updatedState.poop && !updatedState.sleeping){
            attentionNotification(applicationContext, "Your Tamagotchi has pooped!")

            createSingleWorker<PoopSickWork>(
                applicationContext,
                Duration.ofMinutes(30),
                "poop_sick",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}