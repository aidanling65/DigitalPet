package com.example.tamagotchi.domain.workers.evolution

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.createPeriodicWorker
import com.example.tamagotchi.domain.workers.regular.DeathWork
import com.example.tamagotchi.domain.workers.regular.HappinessDecayWork
import com.example.tamagotchi.domain.workers.regular.HungerDecayWork
import com.example.tamagotchi.domain.workers.regular.MisbehavingWork
import com.example.tamagotchi.domain.workers.regular.PoopWork
import com.example.tamagotchi.domain.workers.regular.SickWork
import com.example.tamagotchi.domain.workers.regular.SleepWork
import com.example.tamagotchi.domain.workers.regular.StepWork
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.showNotification
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

fun babyChildEvolve(context: Context, currentState: TamagotchiState): TamagotchiState {
    showNotification(context, "Your Tamagotchi has evolved!")

    WorkManager.getInstance(context).cancelAllWorkByTag("hunger_happiness")

    createPeriodicWorker<PoopWork>(
        context,
        Duration.ofMinutes(30),
        Duration.ofMinutes(30),
        "poop",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HappinessDecayWork>(
        context,
        Duration.ofMinutes(15),
        Duration.ofMinutes(60),
        "happiness",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HungerDecayWork>(
        context,
        Duration.ofMinutes(15),
        Duration.ofMinutes(45),
        "hunger",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<SleepWork>(
        context,
        Duration.ofMinutes(5),
        Duration.ofMinutes(5),
        "sleep",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<MisbehavingWork>(
        context,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "misbehaving",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<SickWork>(
        context,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "sick",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    val currentTime = LocalTime.now(ZoneId.systemDefault())
    createPeriodicWorker<StepWork>(
        context,
        Duration.between(currentTime, LocalTime.MAX),
        Duration.ofHours(24),
        "step_reset",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<DeathWork>(
        context,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "death",
        ExistingPeriodicWorkPolicy.REPLACE
    )

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD
    )

    scheduleEvolutionWork(context,updatedState)
    return  updatedState
}
