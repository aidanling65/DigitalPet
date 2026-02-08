package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class SleepMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var mistake = false
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
            if(it.sleeping && it.light){
                mistake = true
                it.copy(
                    mentalMistakes = it.mentalMistakes + 1
                )
            }
            it
        }

        if(mistake) {
            updateHistory(applicationContext) {
                it.copy(mistakesMade = it.mistakesMade + 1)
            }
        }

        return Result.success()
    }
}