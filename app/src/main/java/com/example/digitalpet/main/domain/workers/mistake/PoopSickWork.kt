package com.example.digitalpet.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration

class PoopSickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState { it ->
            if (it.sleeping || it.paused) {
                createSingleWorker<PoopSickWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "poop_check",
                    ExistingWorkPolicy.REPLACE
                )
                it
            }
            else if (it.poop && !it.sick) {
                attentionNotification(applicationContext, "Your tamagotchi is sick!")
                createSingleWorker<SickMistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "sick_mistake",
                    ExistingWorkPolicy.REPLACE
                )
                it.copy(
                    sick = true
                )

            }
            else{
                it
            }
        }

        return Result.success()
    }
}