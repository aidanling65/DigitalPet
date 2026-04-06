package com.example.digitalpet.main.domain.workers.evolution

import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.PetState

fun childTeenEvolve(
    currentState: PetState,
): Pair<PetState, EvolutionLog> {
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