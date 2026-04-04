package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.PetState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification

fun childTeenEvolve(
    context: Context,
    currentState: PetState,
    showNotification: Boolean = true
): Pair<PetState, EvolutionLog> {
    if(showNotification) {
        showNotification(context, "Your Tamagotchi has evolved!", EVOLVE_ID)
    }

    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.mistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )

    val evolutionLog = EvolutionLog(
        ageStage = updatedState.ageStage,
        evolutionType = updatedState.animations
    )

    return updatedState to evolutionLog
}