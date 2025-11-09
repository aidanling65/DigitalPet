package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import java.time.Duration

class HappyWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork() : Result{
        val current = repository.getState()
        if(current.happiness == 0){
            val updated = current.copy(
                mentalMistakes = current.mentalMistakes + 1
            )
            repository.saveState(updated)
        }
        else{
            createSingleWorker<HappyWork>(
                applicationContext, Duration.ofMinutes(30), "happy",
                ExistingWorkPolicy.REPLACE
            )
        }
        return Result.success()
    }
}