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
    private val repository = TamagotchiRepository.getInstance(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            if (it.physicalMistakes + it.mentalMistakes >= 5) {
                death(applicationContext, it)
            } else{
                it
            }
        }

        return Result.success()
    }
}