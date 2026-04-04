package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.mistake.PoopSickWork
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration
import kotlin.random.Random

class PoopWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

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
            attentionNotification(applicationContext, "Your pet has pooped!")

            createSingleWorker<PoopSickWork>(
                applicationContext,
                Duration.ofMinutes(30),
                "poop_sick",
                ExistingWorkPolicy.REPLACE
            )

            updateHistory(applicationContext) {
                it.copy(timesPooped = it.timesPooped + 1)
            }
        }

        return Result.success()
    }
}