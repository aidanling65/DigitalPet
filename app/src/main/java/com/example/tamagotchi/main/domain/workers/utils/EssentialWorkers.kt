package com.example.tamagotchi.domain.workers.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.baby.BabyHungerHappinessWork
import com.example.tamagotchi.domain.workers.baby.BabyPoopWork
import com.example.tamagotchi.domain.workers.baby.BabySickWork
import com.example.tamagotchi.domain.workers.baby.BabySleepWork
import com.example.tamagotchi.domain.workers.periodic.DeathWork
import com.example.tamagotchi.domain.workers.periodic.FitnessWork
import com.example.tamagotchi.domain.workers.periodic.HappinessDecayWork
import com.example.tamagotchi.domain.workers.periodic.HungerDecayWork
import com.example.tamagotchi.domain.workers.periodic.MisbehavingWork
import com.example.tamagotchi.domain.workers.periodic.PoopWork
import com.example.tamagotchi.domain.workers.periodic.SickWork
import com.example.tamagotchi.domain.workers.periodic.SleepWork
import com.example.tamagotchi.domain.workers.periodic.StepWork
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

fun scheduleEssentialWorkers(context: Context, currentState: TamagotchiState){

    scheduleEvolutionWork(context,currentState)

    when(currentState.ageStage){
        AgeStage.EGG -> return
        AgeStage.DEAD -> return
        AgeStage.BABY -> {
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
        }
        else -> {
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
                Duration.ofMinutes(30),
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
            createPeriodicWorker<FitnessWork>(
                context,
                Duration.ofHours(24),
                Duration.ofHours(48),
                "fitness",
                ExistingPeriodicWorkPolicy.REPLACE
            )
            createPeriodicWorker<DeathWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(15),
                "death",
                ExistingPeriodicWorkPolicy.REPLACE
            )
        }
    }
}