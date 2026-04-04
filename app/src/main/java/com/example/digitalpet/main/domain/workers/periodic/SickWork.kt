package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.mistake.SickMistakeWork
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration
import kotlin.random.Random

class SickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        val updatedState = repository.updateState{ current ->
            if(current.sleeping || current.sick || current.paused){
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
            attentionNotification(applicationContext, "Your pet is sick!")

            createSingleWorker<SickMistakeWork>(
                applicationContext,
                Duration.ofMinutes(30),
                "sick",
                ExistingWorkPolicy.REPLACE
            )

            updateHistory(applicationContext) {
                it.copy(timesSick = it.timesSick + 1)
            }
        }

        return Result.success()
    }
}