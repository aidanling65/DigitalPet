package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import java.time.Duration

class HungerWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork() : Result{
        val current = repository.getState()
        if(current.hunger == 0){
            val updated = current.copy(
                physicalMistakes = current.physicalMistakes + 1
            )
            repository.saveState(updated)
        }
        else{
            createSingleWorker<HungerWork>(
                applicationContext, Duration.ofMinutes(30), "hunger",
                ExistingWorkPolicy.REPLACE
            )
        }
        return Result.success()
    }
}