package com.example.tamagotchi.main.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository

class DisciplineMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository.getInstance(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if (it.misbehaving) {
                it.copy(
                    misbehaving = false,
                    mentalMistakes = it.mentalMistakes + 1
                )
            }
            else{
                it
            }
        }

        return Result.success()
    }
}