package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.evolution.death

class DeathWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if(it.ageStage.mistakesLimit == null || it.sleeping){
                it
            }
            else if (it.physicalMistakes + it.mentalMistakes >= it.ageStage.mistakesLimit) {
                death(applicationContext, it)
            } else{
                it
            }
        }

        return Result.success()
    }
}