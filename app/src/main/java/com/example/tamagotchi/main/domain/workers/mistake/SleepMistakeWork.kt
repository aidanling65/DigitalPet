package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class SleepMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if(it.paused){
                createSingleWorker<SleepMistakeWork>(
                    applicationContext,
                    Duration.ofHours(1),
                    "sleep_mistake",
                    ExistingWorkPolicy.REPLACE,
                )
                it
            }
            it.copy(
                mentalMistakes = if (it.sleeping && it.light) it.mentalMistakes + 1 else it.mentalMistakes
            )
        }

        return Result.success()
    }
}