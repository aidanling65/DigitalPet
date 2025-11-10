package com.example.tamagotchi.domain.workers.evolution

import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.showNotification

fun childTeenEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance, "Your Tamagotchi has evolved!")

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )
    scheduleEvolutionWork(MyApp.instance, updatedState)
    return updatedState
}