package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.mistake.StupidMistakeWork
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration

class BrainrotWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        val updatedState = repository.updateState {
            it.copy(
                intelligence = (it.intelligence - 1).coerceAtLeast(0)
            )
        }

        if(updatedState.intelligence == 0){
            attentionNotification(applicationContext, "Your pet is stupid!")

            createSingleWorker<StupidMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "stupid",
                ExistingWorkPolicy.REPLACE
            )
        }


        return Result.success()
    }
}