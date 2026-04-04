package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.PetRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class StupidMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        var mistake = false

        repository.updateState {
            if (it.sleeping || it.paused) {
                createSingleWorker<StupidMistakeWork>(
                    applicationContext,
                    Duration.ofHours(1),
                    "stupid_mistake",
                    ExistingWorkPolicy.REPLACE,
                )
                it
            }

            if (it.intelligence == 0) {
                mistake = true
                it.copy(
                    mistakes = it.mistakes + 1
                )
            }

            else{
                it
            }
        }

        if(mistake) {
            updateHistory(applicationContext) {
                it.copy(mistakesMade = it.mistakesMade + 1)
            }
        }
        return Result.success()
    }
}