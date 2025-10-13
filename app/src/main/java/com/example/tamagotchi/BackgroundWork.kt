package com.example.tamagotchi

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import kotlin.random.Random

class TamagotchiWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)
    override fun doWork(): Result {
        Log.d("msg", "Periodic update")
        runBlocking {
            val currentState = repository.getState()
            val updatedState = currentState.copy(
                hunger = if (currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                happiness = if (currentState.happiness > 0) currentState.happiness - 1 else currentState.happiness,
                misbehaving = Random.nextInt(1, 4) == 1,
                poop = Random.nextInt(1, 4) == 1
            )

            repository.saveState(updatedState)
        }

        return Result.success()
    }
}

