package com.example.tamagotchi

import android.content.Context
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
        runBlocking{
            val currentState = repository.getState()
            val updatedState = currentState.copy(
                hunger = currentState.hunger - 1,
                happiness =  currentState.happiness - 1,
                misbehaving = Random.nextInt(1,4) == 1,
                poop = Random.nextInt(1,4) == 1
            )

            repository.saveState(updatedState)
        }

        return Result.success()
    }

}

