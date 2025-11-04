package com.example.tamagotchi.workers

import android.content.Context
import android.util.Log
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.tamagotchi.tamagotchi.AgeStage
import com.example.tamagotchi.tamagotchi.EvolutionAnimations
import com.example.tamagotchi.tamagotchi.MAX_HUNGER
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.tamagotchi.TamagotchiRepository
import com.example.tamagotchi.tamagotchi.TamagotchiState
import com.example.tamagotchi.showNotification
import kotlinx.coroutines.runBlocking
import java.time.LocalTime
import java.time.ZoneId
import kotlin.random.Random

class BackgroundWork(
    appContext: Context,
    workerParams: WorkerParameters
) : Worker(appContext, workerParams) {

    private val repository = TamagotchiRepository(appContext)

    private fun calculateHappiness(currentState: TamagotchiState): Int {
        if (currentState.sleeping && !currentState.light) {
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

    private fun calculateWeight(currentState: TamagotchiState): Int {
        if (currentState.sleeping) {
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

    private fun calculatePhysicalMistakes(currentState: TamagotchiState): Int {
        var currentMistakes = currentState.physicalMistakes
        if (currentState.sleeping) {
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

    private fun calculateMentalMistakes(currentState: TamagotchiState): Int {
        var currentMistakes = currentState.mentalMistakes
        if (currentState.happiness == 0 && !currentState.sleeping) {
            currentMistakes++
        }
        if (currentState.sleeping && currentState.light) {
            currentMistakes++
        }
        return currentMistakes
    }

    private fun die(currentState: TamagotchiState) : TamagotchiState {
        showNotification("Your Tamagotchi has died!")
        WorkManager.getInstance(MyApp.Companion.instance).cancelAllWorkByTag("evolve")
        val updatedState = currentState.copy(
            ageStage = AgeStage.DEAD,
            weight = AgeStage.DEAD.minimumWeight,
            animations = EvolutionAnimations.DEAD
        )
        return updatedState
    }

    private fun sleepAndAge(currentState: TamagotchiState) : TamagotchiState {
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        val sleeping  = currentTime.isAfter(
            currentState.ageStage.bedTime ?: LocalTime.of(
                23,
                59,
                59
            )
        ) || currentTime.isBefore(
            currentState.ageStage.wakeTime ?: LocalTime.of(
                0,
                0,
                0
            )
        )

        return currentState.copy(
            age = if(currentState.sleeping && !sleeping) currentState.age + 1 else currentState.age,
            sleeping = sleeping
        )
    }

    override fun doWork(): Result {
        Log.d("Background Work", "Periodic update")
        runBlocking {
            val currentState = repository.getState()
            val sleepAgeState = sleepAndAge(currentState).copy()
            val updatedState =
                if(currentState.physicalMistakes + currentState.mentalMistakes >= 5) die(currentState).copy()
                else if (currentState.ageStage == AgeStage.DEAD) currentState.copy() else currentState.copy(
                    physicalMistakes = calculatePhysicalMistakes(currentState),
                    mentalMistakes = calculateMentalMistakes(currentState),
                    hunger = if (currentState.hunger > 0 && !currentState.sleeping) currentState.hunger - 1 else currentState.hunger,
                    happiness = calculateHappiness(currentState),
                    weight = calculateWeight(currentState),
                    misbehaving = !currentState.sleeping && Random.nextInt(1, 4) == 1,
                    poop = !currentState.sleeping && Random.nextInt(1, 4) == 1,
                    sleeping = sleepAgeState.sleeping,
                    age = sleepAgeState.age,
                )
            repository.saveState(updatedState)
        }
        return Result.success()
    }
}

