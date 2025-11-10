package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.utils.showNotification
import kotlin.random.Random
import java.time.Duration

class PoopWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)
    val rng = Random(2345)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(rng.nextFloat() < 0.5f && !state.poop){
            showNotification(applicationContext, "Your Tamagotchi has pooped!")
            state = state.copy(
                poop = true
            )
        }

        createSingleWorker<SickWork>(
            applicationContext,
            Duration.ofMinutes(30),
            "sick",
            ExistingWorkPolicy.REPLACE
        )
        repository.saveState(state)

        return Result.success()
    }
}