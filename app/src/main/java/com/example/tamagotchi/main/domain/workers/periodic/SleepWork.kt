package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.mistake.SleepMistakeWork
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

class SleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        val currentTime = LocalTime.now(ZoneId.systemDefault())

        var slept = false

        repository.updateState { current ->
            val isBedTime = current.bedTime
            val isWakeTime = current.wakeTime
            val shouldBeSleeping = currentTime.isAfter(isBedTime) || currentTime.isBefore(isWakeTime)

            if (!current.sleeping && shouldBeSleeping) {
                slept = true
                attentionNotification(applicationContext, "Your tamagotchi has gone to sleep")
                createSingleWorker<SleepMistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "sleep_mistake",
                    ExistingWorkPolicy.REPLACE
                )
            } else if (current.sleeping && !shouldBeSleeping) {
                attentionNotification(applicationContext, "Your tamagotchi has awoken")
            }

            current.copy(
                age = if (current.sleeping && !shouldBeSleeping) current.age + 1 else current.age,
                light = if (!shouldBeSleeping) true else current.light,
                sleeping = shouldBeSleeping
            )
        }

        if(slept) {
            updateHistory(applicationContext) {
                it.copy(timesSlept = it.timesSlept + 1)
            }
        }
        return Result.success()
    }
}