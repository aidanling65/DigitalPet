package com.example.tamagotchi.main.domain.workers.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.domain.workers.baby.BabyHungerHappinessWork
import com.example.tamagotchi.main.domain.workers.baby.BabyPoopWork
import com.example.tamagotchi.main.domain.workers.baby.BabySickWork
import com.example.tamagotchi.main.domain.workers.baby.BabySleepWork
import com.example.tamagotchi.main.domain.workers.evolution.EvolutionWork
import com.example.tamagotchi.main.domain.workers.periodic.BrainrotWork
import com.example.tamagotchi.main.domain.workers.periodic.DeathWork
import com.example.tamagotchi.main.domain.workers.periodic.FitnessWork
import com.example.tamagotchi.main.domain.workers.periodic.HappinessDecayWork
import com.example.tamagotchi.main.domain.workers.periodic.HungerDecayWork
import com.example.tamagotchi.main.domain.workers.periodic.MisbehavingWork
import com.example.tamagotchi.main.domain.workers.periodic.PoopWork
import com.example.tamagotchi.main.domain.workers.periodic.SickWork
import com.example.tamagotchi.main.domain.workers.periodic.SleepWork
import com.example.tamagotchi.step_tracker.StepCounterWorker
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

fun scheduleEssentialWorkers(
    context: Context,
    currentState: TamagotchiState,
    policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
) {

    scheduleEvolutionWork(context, currentState)

    when (currentState.ageStage) {
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
            createPeriodicWorker<StepCounterWorker>(
                context,
                Duration.ofSeconds(5),
                Duration.ofMinutes(15),
                "step_worker",
                policy
            )
            scheduleEvolutionWork(context, currentState)
        }

        else -> {
            val now = LocalDateTime.now()
            val midnightTonight = LocalDate.now(ZoneId.systemDefault()).plusDays(1)
                .atStartOfDay(ZoneId.systemDefault()).toInstant()
            val initialDelay = Duration.between(now, midnightTonight)

            val lastEvolution = currentState.lastEvolve

            if (currentState.ageStage.stageLength != null) {
                val sinceEvolution = Duration.between(lastEvolution, now)
                val evolveTime = currentState.ageStage.stageLength - sinceEvolution

                createSingleWorker<EvolutionWork>(
                    context,
                    evolveTime,
                    "evolve",
                    ExistingWorkPolicy.REPLACE
                )
            }

            createPeriodicWorker<PoopWork>(
                context,
                Duration.ofMinutes(30),
                Duration.ofMinutes(30),
                "poop",
                policy
            )
            createPeriodicWorker<HappinessDecayWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(60),
                "happiness",
                policy
            )
            createPeriodicWorker<HungerDecayWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(45),
                "hunger",
                policy
            )
            createPeriodicWorker<SleepWork>(
                context,
                Duration.ofMinutes(5),
                Duration.ofMinutes(5),
                "sleep",
                policy
            )
            createPeriodicWorker<MisbehavingWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(30),
                "misbehaving",
                policy
            )
            createPeriodicWorker<BrainrotWork>(
                context,
                initialDelay,
                Duration.ofHours(24),
                "brainrot",
                policy
            )
            createPeriodicWorker<SickWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(15),
                "sick",
                policy
            )
            createPeriodicWorker<StepCounterWorker>(
                context,
                Duration.ofMinutes(5),
                Duration.ofMinutes(15),
                "step_worker",
                policy
            )
            createPeriodicWorker<FitnessWork>(
                context,
                Duration.ofHours(24),
                Duration.ofHours(48),
                "fitness",
                policy
            )
            createPeriodicWorker<DeathWork>(
                context,
                Duration.ofMinutes(15),
                Duration.ofMinutes(15),
                "death",
                policy
            )
        }
    }
}