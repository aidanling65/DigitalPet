package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.showNotification

fun childTeenEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has evolved!")

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )

    return updatedState
}