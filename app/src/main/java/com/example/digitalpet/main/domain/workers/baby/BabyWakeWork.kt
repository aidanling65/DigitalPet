package com.example.digitalpet.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.utils.attentionNotification

class BabyWakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

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