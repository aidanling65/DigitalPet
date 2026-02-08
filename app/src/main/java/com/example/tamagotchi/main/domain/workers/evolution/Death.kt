package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.data_logging.storeEvolution
import com.example.tamagotchi.main.data.data_logging.updateHistory
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification
import kotlinx.coroutines.runBlocking

fun death(context: Context, currentState: TamagotchiState): TamagotchiState {
    WorkManager.getInstance(context).cancelAllWork()
    showNotification(context,"Your Tamagotchi has died!", EVOLVE_ID)
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
        storeEvolution(context, EvolutionLog(
            ageStage = updatedState.ageStage,
            evolutionType = updatedState.animations
        ))
    }

    return updatedState
}