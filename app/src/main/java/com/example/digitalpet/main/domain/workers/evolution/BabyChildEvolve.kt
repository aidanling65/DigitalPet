package com.example.digitalpet.main.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.digitalpet.main.data.data_logging.EvolutionLog
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.utils.EVOLVE_ID
import com.example.digitalpet.main.utils.showNotification

fun babyChildEvolve(context: Context, currentState: PetState, showNotification: Boolean = true): Pair<PetState, EvolutionLog> {

    if(showNotification) {
        showNotification(context, "Your Tamagotchi has evolved!", EVOLVE_ID)
    }

    WorkManager.getInstance(context).cancelAllWorkByTag("hunger_happiness")

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD
    )

    val evolutionLog = EvolutionLog(
            ageStage = updatedState.ageStage,
            evolutionType = updatedState.animations
    )

    return  updatedState to evolutionLog
}
