package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository

class HappinessDecayWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)
    var sinceLast = 0

    override suspend fun doWork(): Result {
        var state = repository.getState()
        sinceLast = (sinceLast + 1) % 4

        state = state.copy(
            mentalMistakes = if(state.happiness == 0) state.mentalMistakes + 1 else state.mentalMistakes,
            happiness =  if(sinceLast == 0 && state.happiness > 0) state.happiness - 1 else state.happiness
        )

        repository.saveState(state)

        return Result.success()
    }
}