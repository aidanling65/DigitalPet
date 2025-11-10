package com.example.tamagotchi.domain.workers.evolution

import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.showNotification

fun teenAdultEvolve(currentState: TamagotchiState): TamagotchiState {
    val discipline = currentState.discipline
    val mistakes = currentState.mentalMistakes + currentState.physicalMistakes

    val nextAnimation = when (currentState.animations) {
        EvolutionAnimations.TEEN_1 -> when {
            discipline == MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_1
            discipline == MAX_DISCIPLINE && mistakes > 2 -> EvolutionAnimations.ADULT_2

            discipline <= MAX_DISCIPLINE / 2 && mistakes <= 2 -> EvolutionAnimations.ADULT_3
            discipline <= MAX_DISCIPLINE / 2 && mistakes > 2 -> EvolutionAnimations.ADULT_6

            discipline < MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_2
            else -> EvolutionAnimations.ADULT_5
        }

        else -> when {
            discipline == MAX_DISCIPLINE -> EvolutionAnimations.ADULT_4
            discipline <= MAX_DISCIPLINE / 2 -> EvolutionAnimations.ADULT_5
            else -> EvolutionAnimations.ADULT_6
        }
    }

    showNotification(MyApp.instance, "Your Tamagotchi has evolved!")
    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations = nextAnimation
    )

    scheduleEvolutionWork(MyApp.instance, updatedState)
    return updatedState
}