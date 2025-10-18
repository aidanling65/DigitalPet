package com.example.tamagotchi

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.ZoneId
import kotlin.random.Random

class TamagotchiWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    fun calculateHappiness(currentState: TamagotchiState): Int {
        if(currentState.sleeping && !currentState.light){
            return currentState.happiness
        }

        var currentLoss = 1
        if (currentState.sleeping && currentState.light && Random.nextInt(0, 2) == 2) {
            currentLoss++
        }
        if (currentState.poop && Random.nextInt(0, 2) == 2) {
            currentLoss++
        }
        var happiness = currentState.happiness - currentLoss
        if (happiness < 0) happiness = 0

        return happiness
    }

    fun calculateWeight(currentState: TamagotchiState): Int {
        if(currentState.sleeping){
            return currentState.weight
        }

        val randomValue = Random.nextInt(1, MAX_HUNGER)
        val currentHunger = currentState.hunger
        var weightLoss = 0
        if (randomValue > currentHunger) {
            weightLoss = randomValue - currentHunger
        }

        val weight = currentState.weight - weightLoss
        val minimumWeight = currentState.ageStage.minimumWeight
        return if (weight < minimumWeight) minimumWeight else weight
    }

    fun calculatePhysicalMistakes(currentState: TamagotchiState): Int {
        var currentMistakes = currentState.physicalMistakes
        if(currentState.sleeping){
            return currentMistakes
        }

        if (currentState.misbehaving) {
            currentMistakes++
        }
        if (currentState.sick) {
            currentMistakes++
        }
        if (currentState.hunger == 0) {
            currentMistakes++
        }

        return currentMistakes
    }

    fun calculateMentalMistakes(currentState: TamagotchiState): Int {
        var currentMistakes = currentState.mentalMistakes
        if (currentState.happiness == 0 && !currentState.sleeping) {
            currentMistakes++
        }
        if (currentState.sleeping && currentState.light) {
            currentMistakes++
        }
        return currentMistakes
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        Log.d("msg", "Periodic update")
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        runBlocking {
            val currentState = repository.getState()
            val updatedState = currentState.copy(
                hunger = if (currentState.hunger > 0 && !currentState.sleeping) currentState.hunger - 1 else currentState.hunger,
                happiness = calculateHappiness(currentState),
                physicalMistakes = calculatePhysicalMistakes(currentState),
                mentalMistakes = calculateMentalMistakes(currentState),
                weight = calculateWeight(currentState),
                misbehaving = !currentState.sleeping && Random.nextInt(1, 4) == 1,
                poop = Random.nextInt(1, 4) == 1,
                sleeping = currentTime.isAfter(
                    currentState.ageStage.bedTime ?: LocalTime.of(
                        23,
                        59,
                        59
                    )
                ) || currentTime.isBefore(currentState.ageStage.wakeTime ?: LocalTime.of(0, 0, 0)),
            )

            repository.saveState(updatedState)
        }
        return Result.success()
    }
}

