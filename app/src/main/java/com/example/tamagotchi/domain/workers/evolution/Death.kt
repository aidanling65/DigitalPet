package com.example.tamagotchi.domain.workers.evolution

import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.utils.showNotification

fun death(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance,"Your Tamagotchi has died!")
    return baseEvolve(currentState).copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
}