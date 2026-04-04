package com.example.digitalpet.main.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.data.data_logging.storeEvolution
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.utils.EVOLVE_ID
import com.example.digitalpet.main.utils.showNotification
import kotlinx.coroutines.runBlocking

fun death(
    context: Context,
    currentState: PetState,
    showNotification: Boolean = true
): PetState {
    WorkManager.getInstance(context).cancelAllWork()

    if (showNotification) {
        showNotification(context, "Your pet has died!", EVOLVE_ID)
    }

    val updatedState = baseEvolve(currentState).copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD,
    )

    runBlocking {
        updateHistory(context) {
            it.copy(
                deaths = it.deaths + 1
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