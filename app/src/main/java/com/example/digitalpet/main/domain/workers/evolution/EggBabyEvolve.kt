package com.example.digitalpet.main.domain.workers.evolution

import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.PetState

fun eggBabyEvolve(currentState: PetState): Pair<PetState, EvolutionLog> {
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