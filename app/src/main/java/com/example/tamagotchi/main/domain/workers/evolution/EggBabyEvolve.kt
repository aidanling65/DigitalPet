package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.showNotification

fun eggBabyEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has hatched!")

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )

    return updatedState
}