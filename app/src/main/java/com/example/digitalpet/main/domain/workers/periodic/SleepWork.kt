package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.mistake.SleepMistakeWork
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

class SleepWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        val currentTime = LocalTime.now(ZoneId.systemDefault())

        var slept = false
        var aged = false

        repository.updateState { current ->
            val isBedTime = current.bedTime
            val isWakeTime = current.wakeTime
            val shouldBeSleeping =
                currentTime.isAfter(isBedTime) || currentTime.isBefore(isWakeTime)

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
                aged = true
                attentionNotification(applicationContext, "Your tamagotchi has awoken")
            }

            current.copy(
                age = if (current.sleeping && !shouldBeSleeping) current.age + 1 else current.age,
                light = if (!shouldBeSleeping) true else current.light,
                sleeping = shouldBeSleeping
            )
        }

        updateHistory(applicationContext) {
            it.copy(
                timesSlept = if (slept) it.timesSlept + 1 else it.timesSlept,
                age = if (aged) it.age + 1 else it.age
            )
        }
        return Result.success()
    }
}