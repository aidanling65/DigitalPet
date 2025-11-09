package com.example.tamagotchi.domain.workers.regular

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.MAX_HUNGER
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.regular.HungerWork
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.utils.showNotification
import kotlinx.coroutines.runBlocking
import java.time.Duration
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
        if (currentState.sleeping && currentState.light && Random.Default.nextInt(0, 2) == 2) {
            currentLoss++
        }
        if (currentState.poop && Random.Default.nextInt(0, 2) == 2) {
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

        val randomValue = Random.Default.nextInt(1, MAX_HUNGER)
        val currentHunger = currentState.hunger
        var weightLoss = 0
        if (randomValue > currentHunger) {
            weightLoss = randomValue - currentHunger
        }

        val weight = currentState.weight - weightLoss
        val minimumWeight = currentState.ageStage.minimumWeight
        return if (weight < minimumWeight) minimumWeight else weight
    }

    private fun calculateMistakes(currentState: TamagotchiState) {
        when {
            currentState.sleeping -> {
                if (currentState.light) {
                    createSingleWorker<MistakeWork>(
                        applicationContext,
                        Duration.ofMinutes(15),
                        "misbehaving",
                        ExistingWorkPolicy.REPLACE,
                        workDataOf(
                            "statName" to "misbehaving",
                            "interval" to 30,
                            "mistakeType" to "physical",
                        )
                    )
                }
                return
            }

            currentState.misbehaving -> {
                showNotification(applicationContext, "You Tamagotchi is misbehaving!")
                createSingleWorker<MistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "misbehaving",
                    ExistingWorkPolicy.REPLACE,
                    workDataOf(
                        "statName" to "misbehaving",
                        "interval" to 30,
                        "mistakeType" to "physical",
                    )
                )
            }

            currentState.sick -> {
                showNotification(applicationContext, "You Tamagotchi is sick!")
                createSingleWorker<MistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "misbehaving",
                    ExistingWorkPolicy.REPLACE,
                    workDataOf(
                        "statName" to "misbehaving",
                        "interval" to 30,
                        "mistakeType" to "physical",
                    )
                )
            }

            currentState.hunger == 0 -> {
                showNotification(applicationContext, "You Tamagotchi is hungry!")
                createSingleWorker<MistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "hunger",
                    ExistingWorkPolicy.REPLACE,
                    workDataOf(
                        "statName" to "hunger",
                        "interval" to 30,
                        "mistakeType" to "physical",
                    )
                )
            }

            currentState.poop -> {
                showNotification(applicationContext, "You Tamagotchi has pooped!")
                createSingleWorker<MistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "poop",
                    ExistingWorkPolicy.REPLACE,
                    workDataOf(
                        "statName" to "poop",
                        "interval" to 30,
                        "mistakeType" to "physical",
                    )
                )
            }

            currentState.happiness == 0 -> {
                showNotification(applicationContext, "You Tamagotchi is sad!")
                createSingleWorker<MistakeWork>(
                    applicationContext,
                    Duration.ofMinutes(15),
                    "happiness",
                    ExistingWorkPolicy.REPLACE,
                    workDataOf(
                        "statName" to "happiness",
                        "interval" to 30,
                        "mistakeType" to "physical",
                    )
                )
            }

            else -> return
        }
    }

    private fun die(currentState: TamagotchiState): TamagotchiState {
        showNotification(applicationContext, "Your Tamagotchi has died!")
        WorkManager.Companion.getInstance(MyApp.Companion.instance).cancelAllWorkByTag("evolve")
        val updatedState = currentState.copy(
            ageStage = AgeStage.DEAD,
            weight = AgeStage.DEAD.minimumWeight,
            animations = EvolutionAnimations.DEAD
        )
        return updatedState
    }

    private fun sleepAndAge(currentState: TamagotchiState): TamagotchiState {
        val currentTime = LocalTime.now(ZoneId.systemDefault())
        val sleeping = currentTime.isAfter(
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
        if (sleeping) {
            showNotification(applicationContext, "Your Tamagotchi has gone to bed")
        }

        return currentState.copy(
            age = if (currentState.sleeping && !sleeping) currentState.age + 1 else currentState.age,
            sleeping = sleeping
        )
    }

    override fun doWork(): Result {
        Log.d("Background Work", "Periodic update")
        runBlocking {
            val currentState = repository.getState()
            val sleepAgeState = sleepAndAge(currentState).copy()
            val updatedState =
                if (currentState.physicalMistakes + currentState.mentalMistakes >= 5) die(
                    currentState
                ).copy()
                else if (currentState.ageStage == AgeStage.DEAD) currentState.copy() else currentState.copy(
                    hunger = if (currentState.hunger > 0 && !currentState.sleeping) currentState.hunger - 1 else currentState.hunger,
                    happiness = calculateHappiness(currentState),
                    weight = calculateWeight(currentState),
                    misbehaving = if (currentState.misbehaving) false else (!currentState.sleeping && Random.Default.nextInt(
                        1,
                        4
                    ) == 1),
                    poop = if (currentState.poop) currentState.poop else (!currentState.sleeping && Random.Default.nextInt(
                        1,
                        4
                    ) == 1),
                    sick = if (currentState.sick) currentState.sick else (!currentState.sleeping && Random.Default.nextInt(
                        1,
                        8
                    ) == 1),
                    sleeping = sleepAgeState.sleeping,
                    light = if (!currentState.sleeping) true else currentState.light,
                    age = sleepAgeState.age,
                )
            repository.saveState(updatedState)
            calculateMistakes(currentState)
        }
        return Result.success()
    }
}