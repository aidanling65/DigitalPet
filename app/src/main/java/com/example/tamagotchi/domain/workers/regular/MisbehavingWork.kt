package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import kotlin.random.Random

class MisbehavingWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)
    private val rng = Random(23456)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        state = state.copy(
            mentalMistakes = if(state.misbehaving) state.mentalMistakes + 1 else state.mentalMistakes,
            misbehaving = if(state.misbehaving) false else Random.nextFloat() < state.ageStage.misbehaviorChances
        )

        repository.saveState(state)

        return Result.success()
    }
}