package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.data_logging.storeEvolution
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification
import kotlinx.coroutines.runBlocking

fun childTeenEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has evolved!", EVOLVE_ID)

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )

    runBlocking {
        updateHistory(context) {
            it.copy(
                timesEvolved = it.timesEvolved + 1
            )
        }
        storeEvolution(context, EvolutionLog(
            ageStage = updatedState.ageStage,
            evolutionType = updatedState.animations
        ))
    }

    return updatedState
}