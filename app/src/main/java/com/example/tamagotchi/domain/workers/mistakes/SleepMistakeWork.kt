package com.example.tamagotchi.domain.workers.mistakes

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository

class SleepMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        state = state.copy(
            mentalMistakes = if(state.sleeping && state.light) state.mentalMistakes + 1 else  state.mentalMistakes
        )

        repository.saveState(state)

        return Result.success()
    }
}