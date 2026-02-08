package com.example.tamagotchi.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration


class BabyMisbehavingWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            it.copy(
                misbehaving = true
            )
        }
        updateHistory(applicationContext){
            it.copy(timesMisbehaved = it.timesMisbehaved + 1)
        }

        attentionNotification(applicationContext, "You Tamagotchi is misbehaving")

        createSingleWorker<BabyBehaveWork>(
            applicationContext,
            Duration.ofMinutes(5),
            "wake",
            ExistingWorkPolicy.REPLACE
        )

        return Result.success()
    }
}