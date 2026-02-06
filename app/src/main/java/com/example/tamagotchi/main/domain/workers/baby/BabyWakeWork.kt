package com.example.tamagotchi.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.utils.attentionNotification

class BabyWakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {

        repository.updateState {
            it.copy(
                sleeping = false,
                age = it.age + 1,
                light = true
            )
        }
        attentionNotification(applicationContext, "You Tamagotchi has woken up")
        updateHistory(applicationContext){
            it.copy(
                age = it.age+1
            )
        }
        return Result.success()
    }
}