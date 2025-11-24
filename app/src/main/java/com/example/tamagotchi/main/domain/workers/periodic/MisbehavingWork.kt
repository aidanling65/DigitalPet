package com.example.tamagotchi.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.domain.workers.mistake.DisciplineMistakeWork
import com.example.tamagotchi.main.utils.showNotification
import java.time.Duration
import kotlin.random.Random

class MisbehavingWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {
        var state = repository.getState()
        if(state.misbehaving  || state.sleeping){
            return Result.success()
        }

        val misbehaving = Random.nextFloat() < state.ageStage.misbehaviorChances
        if(misbehaving){
            showNotification(applicationContext, "Your Tamagotchi is misbehaving!")
            state = state.copy(
                misbehaving = true
            )

            createSingleWorker<DisciplineMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "discipline_check",
                ExistingWorkPolicy.REPLACE,
            )

            repository.saveState(state)
        }


        return Result.success()
    }
}