package com.example.tamagotchi.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.utils.attentionNotification

class BabySickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams){
    private val repository = TamagotchiRepository(appContext)

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
