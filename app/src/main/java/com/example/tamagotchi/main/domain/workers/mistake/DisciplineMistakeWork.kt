package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import java.time.Duration

class DisciplineMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
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
                it.copy(
                    misbehaving = false,
                    mentalMistakes = it.mentalMistakes + 1
                )
            } else {
                it
            }
        }

        return Result.success()
    }
}