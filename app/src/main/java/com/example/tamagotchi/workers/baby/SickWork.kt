package com.example.tamagotchi.workers.baby

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tamagotchi.tamagotchi.TamagotchiRepository
import kotlinx.coroutines.runBlocking

class SickWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams){
    private val repository = TamagotchiRepository(appContext)

    override fun doWork(): Result {
        runBlocking {
            val currentState = repository.getState()
            val updatedState = currentState.copy(
                sick = true
            )
            repository.saveState(updatedState)
        }

        return Result.success()
    }
}
