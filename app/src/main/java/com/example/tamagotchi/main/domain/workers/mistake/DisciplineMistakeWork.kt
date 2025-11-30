package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository

class DisciplineMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if (state.misbehaving) {
            repository.updateState {
                it.copy(
                    misbehaving = false,
                    mentalMistakes = state.mentalMistakes + 1
                )
            }
        }

        return Result.success()
    }
}