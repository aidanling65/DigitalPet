package com.example.tamagotchi.domain.workers.mistakes

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository

class HappinessMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentState = repository.getState()
        if(currentState.happiness == 0) {
            val updatedState = currentState.copy(
                mentalMistakes = currentState.mentalMistakes + 1
            )
            repository.saveState(updatedState)
        }

        return Result.success()
    }
}