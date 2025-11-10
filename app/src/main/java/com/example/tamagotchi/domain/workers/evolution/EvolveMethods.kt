package com.example.tamagotchi.domain.workers.evolution

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.utils.showNotification
import com.example.tamagotchi.domain.workers.baby.BabyHungerHappinessWork
import com.example.tamagotchi.domain.workers.baby.BabyPoopWork
import com.example.tamagotchi.domain.workers.baby.BabySickWork
import com.example.tamagotchi.domain.workers.baby.BabySleepWork
import com.example.tamagotchi.domain.workers.createPeriodicWorker
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.domain.workers.regular.HappinessDecayWork
import com.example.tamagotchi.domain.workers.regular.HungerDecayWork
import com.example.tamagotchi.domain.workers.regular.PoopWork
import com.example.tamagotchi.domain.workers.regular.SleepWork
import java.time.Duration

fun baseEvolve(currentState: TamagotchiState) : TamagotchiState {
    return currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0,
        poop = false,
        sick = false
    )
}

fun eggBabyEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance,"Your Tamagotchi has hatched!")

    createSingleWorker<BabyHungerHappinessWork>(MyApp.instance, Duration.ZERO, "hunger_happiness", ExistingWorkPolicy.REPLACE)
    createSingleWorker<BabyPoopWork>(MyApp.instance, Duration.ofMinutes(15), "poop", ExistingWorkPolicy.REPLACE)
    createSingleWorker<BabySickWork>(MyApp.instance, Duration.ofMinutes(30), "sick", ExistingWorkPolicy.REPLACE)
    createSingleWorker<BabySleepWork>(MyApp.instance, Duration.ofMinutes(40), "sleep", ExistingWorkPolicy.REPLACE)

    return baseEvolve(currentState).copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )
}

fun babyChildEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance, "Your Tamagotchi has evolved!")

    /*createPeriodicWorker<BackgroundWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "passive_tasks",
        ExistingPeriodicWorkPolicy.REPLACE
    )*/

    createPeriodicWorker<PoopWork>(
        MyApp.instance,
        Duration.ofMinutes(30),
        Duration.ofMinutes(30),
        "poop",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HappinessDecayWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "happiness",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HungerDecayWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "hunger",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<SleepWork>(
        MyApp.instance,
        Duration.ofMinutes(5),
        Duration.ofMinutes(5),
        "sleep",
        ExistingPeriodicWorkPolicy.REPLACE
    )

    return baseEvolve(currentState).copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD
    )
}

fun childTeenEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance, "Your Tamagotchi has evolved!")

    return baseEvolve(currentState).copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )
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

    showNotification(MyApp.instance,"Your Tamagotchi has evolved!")
    return baseEvolve(currentState).copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations =  nextAnimation
    )
}

fun deadEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance,"Your Tamagotchi has died!")
    return baseEvolve(currentState).copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
}