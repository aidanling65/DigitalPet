package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.utils.showNotification

fun babyChildEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has evolved!")

    WorkManager.getInstance(context).cancelAllWorkByTag("hunger_happiness")

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD
    )

    return  updatedState
}
