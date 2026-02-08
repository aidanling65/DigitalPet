package com.example.tamagotchi.main.domain.workers.periodic

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkerParameters
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.mistake.DisciplineMistakeWork
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.utils.attentionNotification
import java.time.Duration
import kotlin.random.Random

class MisbehavingWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = TamagotchiRepository(appContext)

    override suspend fun doWork(): Result {

        val updatedState = repository.updateState { current ->
            if (current.misbehaving || current.sleeping || current.paused) {
                current
            }
            else{
                if(Random.nextFloat() < current.ageStage.misbehaviorChances){
                    current.copy(
                        misbehaving = true
                    )
                }
                else{
                    current
                }
            }
        }


        if(updatedState.misbehaving && !updatedState.sleeping){
            attentionNotification(applicationContext, "Your Tamagotchi is misbehaving!")

            createSingleWorker<DisciplineMistakeWork>(
                applicationContext,
                Duration.ofMinutes(15),
                "discipline_check",
                ExistingWorkPolicy.REPLACE,
            )
            updateHistory(applicationContext) {
                it.copy(timesMisbehaved = it.timesMisbehaved + 1)
            }
        }
        return Result.success()
    }
}