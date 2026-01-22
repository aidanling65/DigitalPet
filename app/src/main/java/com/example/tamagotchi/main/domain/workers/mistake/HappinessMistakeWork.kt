package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class HappinessMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if(it.sleeping || it.paused){
                createSingleWorker<HappinessMistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "happiness_check",
                    ExistingWorkPolicy.REPLACE
                )
            }
            if (it.happiness == 0  && !it.sleeping) {
                it.copy(
                    mentalMistakes = it.mentalMistakes + 1
                )
            }
            else {
                it
            }
        }
        return Result.success()
    }
}