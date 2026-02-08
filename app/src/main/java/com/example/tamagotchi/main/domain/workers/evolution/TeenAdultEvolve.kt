package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.data_logging.storeEvolution
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification
import kotlinx.coroutines.runBlocking

fun teenAdultEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    val discipline = currentState.discipline
    val mistakes = currentState.mentalMistakes + currentState.physicalMistakes

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

    showNotification(context, "Your Tamagotchi has evolved!", EVOLVE_ID)
    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations = nextAnimation
    )

    runBlocking {
        updateHistory(context) {
            it.copy(
                timesEvolved = it.timesEvolved + 1
            )
        }
        storeEvolution(
            context, EvolutionLog(
                ageStage = updatedState.ageStage,
                evolutionType = updatedState.animations
            )
        )
    }

    return updatedState
}