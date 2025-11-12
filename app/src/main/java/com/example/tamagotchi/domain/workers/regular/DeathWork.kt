package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.evolution.death

class DeathWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.physicalMistakes + state.mentalMistakes >= 5) {
            state = death(state)

            repository.saveState(state)
        }

        return Result.success()
    }
}