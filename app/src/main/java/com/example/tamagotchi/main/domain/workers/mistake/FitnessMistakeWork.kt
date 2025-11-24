package com.example.tamagotchi.domain.workers.mistake

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository

class FitnessMistakeWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.sleeping){
            return Result.success()
        }

        if (state.fitness == 0) {
            state = state.copy(physicalMistakes = state.physicalMistakes + 1, weight =  state.weight + 2)
            repository.saveState(state)
        }

        return Result.success()
    }
}