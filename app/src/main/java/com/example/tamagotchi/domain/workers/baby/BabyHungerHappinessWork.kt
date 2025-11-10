package com.example.tamagotchi.domain.workers.baby
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.utils.showNotification
import kotlinx.coroutines.delay

class BabyHungerHappinessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)
    private val delay : Long = 180000

    override suspend fun doWork() : Result{
        do {
            delay(delay)
            val currentState = repository.getState()
            if(currentState.sleeping)
                continue

            val updatedState = currentState.copy(
                hunger = if(currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                happiness = if(currentState.happiness > 0) currentState. happiness - 1 else currentState.happiness
            )
            if(updatedState.hunger == 0){
                showNotification(applicationContext, "You Tamagotchi is hungry!")
            }
            if(updatedState.happiness == 0) {
                showNotification(applicationContext, "You Tamagotchi is sad!")
            }
            repository.saveState(updatedState)
        } while(currentState.ageStage == AgeStage.BABY)
        return Result.success()
    }
}