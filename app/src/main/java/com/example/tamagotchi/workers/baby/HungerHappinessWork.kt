package com.example.tamagotchi.workers.baby
import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tamagotchi.tamagotchi.AgeStage
import com.example.tamagotchi.tamagotchi.TamagotchiRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

class HungerHappinessWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)
    private val delay : Long = 180000

    override fun doWork() : Result{
        runBlocking {
            do {
                delay(delay)
                val currentState = repository.getState()
                if(currentState.sleeping)
                    continue

                val updatedState = currentState.copy(
                    hunger = if(currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                    happiness = if(currentState.happiness > 0) currentState. happiness - 1 else currentState.happiness
                )

                repository.saveState(updatedState)
            } while(currentState.ageStage == AgeStage.BABY)
        }

        return Result.success()
    }

}