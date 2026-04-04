package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.evolution.death

class DeathWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if(it.ageStage.mistakesLimit == null || it.sleeping || it.paused){
                it
            }
            else if (it.mistakes >= it.ageStage.mistakesLimit) {
                death(applicationContext, it)
            } else{
                it
            }
        }

        return Result.success()
    }
}