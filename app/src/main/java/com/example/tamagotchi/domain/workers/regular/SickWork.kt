package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification
import kotlin.random.Random

class SickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(!state.sick && !state.poop){
            return Result.success()
        }
        state = state.copy(
            physicalMistakes = if(state.sick) state.physicalMistakes + 1 else state.physicalMistakes,
            sick = if(state.poop) true else state.sick
        )

        repository.saveState(state)

        return Result.success()
    }
}