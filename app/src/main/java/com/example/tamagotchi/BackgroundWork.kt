package com.example.tamagotchi

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import kotlin.random.Random

class TamagotchiWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    fun calculateHappiness(currentState: TamagotchiState): Int{
        var currentLoss = 1
        if(currentState.sleeping && currentState.light && Random.nextInt(0,1) == 2){
            currentLoss++
        }
        var happiness =  currentState.happiness - currentLoss
        if(happiness < 0) happiness = 0

        return happiness
    }

    fun calculateWeightLoss(currentState: TamagotchiState): Int{
        val randomValue = Random.nextInt(1,MAX_HUNGER)
        val currentHunger = currentState.hunger
        return if (randomValue > currentHunger) randomValue - currentHunger else 0
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        Log.d("msg", "Periodic update")
        val currentTime = LocalTime.now()
        val sleepTime = LocalTime.of(20,0)
        val wakeTime = LocalTime.of(8,0)

        Log.d("msg", currentTime.toString())

        runBlocking {
            val currentState = repository.getState()

            val updatedState = currentState.copy(
                hunger = if (currentState.hunger > 0) currentState.hunger - 1 else currentState.hunger,
                happiness =  calculateHappiness(currentState),
                weight = currentState.weight - calculateWeightLoss(currentState),
                misbehaving = Random.nextInt(1, 4) == 1,
                poop = Random.nextInt(1, 4) == 1,
                sleeping = currentTime.isAfter(sleepTime) || currentTime.isBefore(wakeTime),
            )

            repository.saveState(updatedState)
        }

        return Result.success()
    }
}

