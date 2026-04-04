package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.PetState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification

fun eggBabyEvolve(context: Context, currentState: PetState, showNotification: Boolean = true): Pair<PetState, EvolutionLog> {
    if(showNotification) {
        showNotification(context, "Your Tamagotchi has hatched!", EVOLVE_ID)
    }

    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )

    val evolutionLog = EvolutionLog(
        ageStage = updatedState.ageStage,
        evolutionType = updatedState.animations
    )

    return updatedState to evolutionLog
}