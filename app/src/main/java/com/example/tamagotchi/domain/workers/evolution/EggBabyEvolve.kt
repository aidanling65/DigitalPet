package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.baby.BabyHungerHappinessWork
import com.example.tamagotchi.domain.workers.baby.BabyPoopWork
import com.example.tamagotchi.domain.workers.baby.BabySickWork
import com.example.tamagotchi.domain.workers.baby.BabySleepWork
import com.example.tamagotchi.domain.workers.createSingleWorker
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.showNotification
import java.time.Duration

fun eggBabyEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has hatched!")

    createSingleWorker<BabyHungerHappinessWork>(
        context,
        Duration.ofMinutes(3),
        "hunger_happiness",
        ExistingWorkPolicy.REPLACE
    )
    createSingleWorker<BabyPoopWork>(
        context,
        Duration.ofMinutes(15),
        "poop",
        ExistingWorkPolicy.REPLACE
    )
    createSingleWorker<BabySickWork>(
        context,
        Duration.ofMinutes(30),
        "sick",
        ExistingWorkPolicy.REPLACE
    )
    createSingleWorker<BabySleepWork>(
        context,
        Duration.ofMinutes(40),
        "sleep",
        ExistingWorkPolicy.REPLACE
    )

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )
    scheduleEvolutionWork(context,updatedState)
    return updatedState
}