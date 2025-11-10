package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification
import kotlin.random.Random

class DisciplineCheckWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.misbehaving) {
            state = state.copy(
                misbehaving = false,
                mentalMistakes = state.mentalMistakes + 1
            )

            repository.saveState(state)
        }

        return Result.success()
    }
}