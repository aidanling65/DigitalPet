package com.example.digitalpet.main.domain.workers.evolution

import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.MAX_DISCIPLINE
import com.example.digitalpet.main.data.model.PetState

fun teenAdultEvolve(
    currentState: PetState,
): Pair<PetState, EvolutionLog> {
    val discipline = currentState.discipline
    val mistakes = currentState.mistakes

    val nextAnimation = when (currentState.animations) {
        EvolutionAnimations.TEEN_1 -> when {
            discipline == MAX_DISCIPLINE && mistakes <= 4 -> EvolutionAnimations.ADULT_1
            discipline == MAX_DISCIPLINE && mistakes > 4 -> EvolutionAnimations.ADULT_2

            discipline <= MAX_DISCIPLINE / 2 && mistakes <= 4 -> EvolutionAnimations.ADULT_3
            discipline <= MAX_DISCIPLINE / 2 && mistakes > 4 -> EvolutionAnimations.ADULT_6

            discipline < MAX_DISCIPLINE && mistakes <= 4 -> EvolutionAnimations.ADULT_2
            else -> EvolutionAnimations.ADULT_5
        }

        else -> when {
            discipline == MAX_DISCIPLINE -> EvolutionAnimations.ADULT_4
            discipline <= MAX_DISCIPLINE / 2 -> EvolutionAnimations.ADULT_5
            else -> EvolutionAnimations.ADULT_6
        }
    }

    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations = nextAnimation
    )

    val evolutionLog = EvolutionLog(
        ageStage = updatedState.ageStage,
        evolutionType = updatedState.animations
    )

    return updatedState to evolutionLog
}