package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository

class HungerDecayWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)
    var sinceLast = 0

    override suspend fun doWork(): Result {
        var state = repository.getState()
        sinceLast = (sinceLast + 1) % 3

        state = state.copy(
            physicalMistakes = if (state.hunger == 0) state.physicalMistakes + 1 else state.physicalMistakes,
            hunger = if (sinceLast == 0 && state.hunger > 0) state.hunger - 1 else state.hunger
        )

        repository.saveState(state)

        return Result.success()
    }
}