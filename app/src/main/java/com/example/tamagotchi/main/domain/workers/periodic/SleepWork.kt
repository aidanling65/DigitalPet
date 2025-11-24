package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.mistake.SleepMistakeWork
import com.example.tamagotchi.main.utils.showNotification
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

class SleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        val sleeping = currentTime.isAfter(
            state.ageStage.bedTime ?: LocalTime.MAX
        ) || currentTime.isBefore(
            state.ageStage.wakeTime ?: LocalTime.MIN
        )

        if(!state.sleeping && sleeping){
            showNotification(applicationContext, "Your tamagotchi has gone to sleep")
            createSingleWorker<SleepMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "sleep_mistake",
                ExistingWorkPolicy.REPLACE
            )
        } else if(state.sleeping && !sleeping){
            showNotification(applicationContext, "Your tamagotchi has awoken")
        }

        state = state.copy(
            age = if(state.sleeping && !sleeping) state.age + 1 else state.age,
            light = if(!sleeping) true else state.light,
            sleeping = sleeping
        )

        repository.saveState(state)

        return Result.success()
    }
}