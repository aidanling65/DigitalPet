package com.example.digitalpet.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.mistake.HungerMistakeWork
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration

class HungerDecayWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        val updatedState = repository.updateState { current ->
            if (current.sleeping || current.paused) {
                current
            } else {
                current.copy(
                    hunger = if (current.hunger > 0) current.hunger - 1 else current.hunger,
                )
            }
        }

        if (updatedState.hunger == 0 && !updatedState.sleeping) {
            attentionNotification(applicationContext, "You Tamagotchi is hungry!")
            createSingleWorker<HungerMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "hunger_mistake",
                ExistingWorkPolicy.REPLACE
            )
        }

        return Result.success()
    }
}