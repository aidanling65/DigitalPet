package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState

fun babyChildEvolve(context: Context, currentState: TamagotchiState): Pair<TamagotchiState, EvolutionLog> {

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
