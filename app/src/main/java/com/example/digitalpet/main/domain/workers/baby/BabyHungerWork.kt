package com.example.digitalpet.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.repository.PetRepository
import com.example.digitalpet.main.domain.workers.utils.createSingleWorker
import com.example.digitalpet.main.utils.attentionNotification
import java.time.Duration

class BabyHungerWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {

        val updatedState = repository.updateState { currentState ->
            if (currentState.ageStage != AgeStage.BABY || currentState.sleeping) {
                currentState
            }
            val updatedState = currentState.copy(
                hunger = if (currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
            )
            if (updatedState.hunger == 0) {
                attentionNotification(applicationContext, "Your pet is hungry!")
            }
            updatedState
        }
        if (updatedState.ageStage == AgeStage.BABY) {
            createSingleWorker<BabyHungerWork>(
                applicationContext,
                Duration.ofMinutes(5),
                "hunger",
                ExistingWorkPolicy.REPLACE,
            )
        }

        return Result.success()
    }
}