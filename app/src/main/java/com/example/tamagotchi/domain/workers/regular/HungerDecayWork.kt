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

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.sleeping){
            return Result.success()
        }
        val sinceLast = (state.hungerDecayCounter + 1) % 3
        var mistakes = state.physicalMistakes
        if(state.hunger == 0){
            mistakes++
        }

        state = state.copy(
            physicalMistakes = mistakes,
            hunger = if (sinceLast == 0 && state.hunger > 0) state.hunger - 1 else state.hunger,
            hungerDecayCounter = sinceLast
        )

        repository.saveState(state)

        return Result.success()
    }
}