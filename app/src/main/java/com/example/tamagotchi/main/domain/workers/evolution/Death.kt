package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.showNotification

fun death(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context,"Your Tamagotchi has died!")
    return baseEvolve(currentState).copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
}