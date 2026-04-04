package com.example.digitalpet.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration

class BabySleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            it.copy(
                sleeping = true
            )
        }
        updateHistory(applicationContext){
            it.copy(timesSlept = it.timesSlept + 1)
        }

        attentionNotification(applicationContext, "Your pet is sleeping")

        createSingleWorker<BabyWakeWork>(
            applicationContext,
            Duration.ofMinutes(5),
            "wake",
            ExistingWorkPolicy.REPLACE
        )

        return Result.success()
    }
}