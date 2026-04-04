package com.example.digitalpet.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class SleepMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

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
                    mistakes = it.mistakes + 1
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