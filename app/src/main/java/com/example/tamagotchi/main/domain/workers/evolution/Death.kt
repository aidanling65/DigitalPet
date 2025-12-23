package com.example.tamagotchi.main.domain.workers.evolution

import android.content.Context
import androidx.work.WorkManager
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.EVOLVE_ID
import com.example.tamagotchi.main.utils.showNotification

fun death(context: Context, currentState: TamagotchiState): TamagotchiState {
    WorkManager.getInstance(context).cancelAllWork()
    showNotification(context,"Your Tamagotchi has died!", EVOLVE_ID)
    return baseEvolve(currentState).copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD,
        sick = false,
        poop = false,
    )
}