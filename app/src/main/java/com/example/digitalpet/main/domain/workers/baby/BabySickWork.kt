package com.example.digitalpet.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.utils.attentionNotification

class BabySickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams){
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            it.copy(
                sick = true
            )
        }
        updateHistory(applicationContext){
            it.copy(timesSick = it.timesSick + 1)
        }

        attentionNotification(applicationContext, "You Tamagotchi is sick!")

        return Result.success()
    }
}
