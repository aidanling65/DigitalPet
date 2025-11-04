package com.example.tamagotchi.workers.baby

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tamagotchi.tamagotchi.TamagotchiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class SleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams){
    private val repository = TamagotchiRepository(appContext)
    private val delay : Long = 300000

    override fun doWork(): Result {
        runBlocking {
            var currentState = repository.getState()
            var updatedState = currentState.copy(
                sleeping = true
            )
            repository.saveState(updatedState)

            delay(delay)
            currentState = repository.getState()
            updatedState = currentState.copy(
                sleeping = false,
                age = currentState.age + 1
            )
            repository.saveState(updatedState)
        }

        return Result.success()
    }
}