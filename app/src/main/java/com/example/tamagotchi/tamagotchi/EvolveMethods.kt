package com.example.tamagotchi.tamagotchi

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.showNotification
import com.example.tamagotchi.workers.BackgroundWork
import com.example.tamagotchi.workers.baby.HungerHappinessWork
import com.example.tamagotchi.workers.baby.PoopWork
import com.example.tamagotchi.workers.baby.SickWork
import com.example.tamagotchi.workers.baby.SleepWork
import com.example.tamagotchi.workers.createPeriodicWorker
import com.example.tamagotchi.workers.createSingleWorker
import java.time.Duration

fun baseEvolve(currentState: TamagotchiState) : TamagotchiState {
    val updatedState =  currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0,
        poop = false,
        sick = false
    )
    return  updatedState
}

fun eggBabyEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has hatched!")

    val updatedState = baseEvolve(currentState.copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    ))

    createSingleWorker<HungerHappinessWork>(Duration.ZERO, "hunger_happiness", ExistingWorkPolicy.KEEP)
    createSingleWorker<PoopWork>(Duration.ofMinutes(15), "poop", ExistingWorkPolicy.KEEP)
    createSingleWorker<SickWork>(Duration.ofMinutes(30), "sick", ExistingWorkPolicy.KEEP)
    createSingleWorker<SleepWork>(Duration.ofMinutes(40), "sleep", ExistingWorkPolicy.KEEP)

    return updatedState
}

fun babyChildEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has evolved!")

    val updatedState = baseEvolve(currentState.copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD,
    ))
    createPeriodicWorker<BackgroundWork>(
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "passive_tasks",
        ExistingPeriodicWorkPolicy.REPLACE
    )

    return updatedState
}

fun childTeenEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has evolved!")

    val updatedState = currentState.copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )
    return  baseEvolve(updatedState)
}

fun teenAdultEvolve(currentState: TamagotchiState): TamagotchiState {
    val discipline = currentState.discipline
    val mistakes = currentState.mentalMistakes + currentState.physicalMistakes

    val nextAnimation = when(currentState.animations){
        EvolutionAnimations.TEEN_1 -> when{
            discipline == MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_1
            discipline == MAX_DISCIPLINE && mistakes > 2 -> EvolutionAnimations.ADULT_2

            discipline <= MAX_DISCIPLINE / 2 && mistakes <= 2 -> EvolutionAnimations.ADULT_3
            discipline <= MAX_DISCIPLINE / 2 && mistakes > 2 -> EvolutionAnimations.ADULT_6

            discipline > MAX_DISCIPLINE / 2 && discipline < MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_2
            else -> EvolutionAnimations.ADULT_5
        }
        else -> when{
            discipline == MAX_DISCIPLINE -> EvolutionAnimations.ADULT_4
            discipline <= MAX_DISCIPLINE / 2 -> EvolutionAnimations.ADULT_5
            else -> EvolutionAnimations.ADULT_6
        }
    }

    showNotification("Your Tamagotchi has evolved!")

    val updatedState = currentState.copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations =  nextAnimation
    )

    return baseEvolve(updatedState)
}

fun deadEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has died!")
    val updatedState = currentState.copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
    return updatedState
}