package com.example.tamagotchi.domain.workers.evolution

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.WorkManager
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.createPeriodicWorker
import com.example.tamagotchi.domain.workers.regular.HappinessDecayWork
import com.example.tamagotchi.domain.workers.regular.HungerDecayWork
import com.example.tamagotchi.domain.workers.regular.MisbehavingWork
import com.example.tamagotchi.domain.workers.regular.PoopWork
import com.example.tamagotchi.domain.workers.regular.SleepWork
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.showNotification
import java.time.Duration

fun babyChildEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification(MyApp.instance, "Your Tamagotchi has evolved!")

    /*createPeriodicWorker<BackgroundWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "passive_tasks",
        ExistingPeriodicWorkPolicy.REPLACE
    )*/

    WorkManager.getInstance(MyApp.instance).cancelAllWorkByTag("sick")
    WorkManager.getInstance(MyApp.instance).cancelAllWorkByTag("hunger_happiness")


    createPeriodicWorker<PoopWork>(
        MyApp.instance,
        Duration.ofMinutes(30),
        Duration.ofMinutes(30),
        "poop",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HappinessDecayWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "happiness",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<HungerDecayWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "hunger",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<SleepWork>(
        MyApp.instance,
        Duration.ofMinutes(5),
        Duration.ofMinutes(5),
        "sleep",
        ExistingPeriodicWorkPolicy.REPLACE
    )
    createPeriodicWorker<MisbehavingWork>(
        MyApp.instance,
        Duration.ofMinutes(15),
        Duration.ofMinutes(15),
        "misbehaving",
        ExistingPeriodicWorkPolicy.REPLACE
    )

    val updatedState =  baseEvolve(currentState).copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD
    )

    scheduleEvolutionWork(MyApp.instance, updatedState)
    return  updatedState
}
