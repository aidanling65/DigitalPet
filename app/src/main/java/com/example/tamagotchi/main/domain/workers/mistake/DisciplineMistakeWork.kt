package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.PetRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class DisciplineMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        var mistake = false

        repository.updateState {
            if (it.paused || it.sleeping) {
                createSingleWorker<DisciplineMistakeWork>(
                    applicationContext,
                    Duration.ofHours(1),
                    "discipline_check",
                    ExistingWorkPolicy.REPLACE
                )
                it
            } else if (it.misbehaving) {
                mistake = true
                it.copy(
                    misbehaving = false,
                    mistakes = it.mistakes + 1
                )
            } else {
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