package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification
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
            state.ageStage.bedTime ?: LocalTime.of(
                23,
                59,
                59
            )
        ) || currentTime.isBefore(
            state.ageStage.wakeTime ?: LocalTime.of(
                0,
                0,
                0
            )
        )

        if(!state.sleeping && sleeping){
            showNotification(applicationContext, "Your tamagotchi has gone to sleep")
        }

        state = state.copy(
            mentalMistakes = if(state.sleeping && state.light) state.mentalMistakes + 1 else state.mentalMistakes,
            age = if(state.sleeping && !sleeping) state.age + 1 else state.age,
            light = if(!sleeping) true else state.light,
            sleeping = sleeping
        )

        repository.saveState(state)

        return Result.success()
    }
}