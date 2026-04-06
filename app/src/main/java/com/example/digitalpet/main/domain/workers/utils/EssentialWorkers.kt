package com.example.digitalpet.main.domain.workers.utils

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.domain.workers.baby.BabyHappinessWork
import com.example.digitalpet.main.domain.workers.baby.BabyHungerWork
import com.example.digitalpet.main.domain.workers.baby.BabyMisbehavingWork
import com.example.digitalpet.main.domain.workers.baby.BabyPoopWork
import com.example.digitalpet.main.domain.workers.baby.BabySickWork
import com.example.digitalpet.main.domain.workers.baby.BabySleepWork
import com.example.digitalpet.main.domain.workers.periodic.BrainrotWork
import com.example.digitalpet.main.domain.workers.periodic.DeathWork
import com.example.digitalpet.main.domain.workers.periodic.FitnessWork
import com.example.digitalpet.main.domain.workers.periodic.HappinessDecayWork
import com.example.digitalpet.main.domain.workers.periodic.HungerDecayWork
import com.example.digitalpet.main.domain.workers.periodic.MisbehavingWork
import com.example.digitalpet.main.domain.workers.periodic.PoopWork
import com.example.digitalpet.main.domain.workers.periodic.SickWork
import com.example.digitalpet.main.domain.workers.periodic.SleepWork
import com.example.digitalpet.main.domain.workers.periodic.StepGoalWork
import com.example.digitalpet.step_tracker.StepCounterWorker
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

fun scheduleEssentialWorkers(
    context: Context,
    currentState: PetState,
    policy: ExistingPeriodicWorkPolicy = ExistingPeriodicWorkPolicy.REPLACE
) {
    scheduleEvolutionWork(context, currentState)

    when (currentState.ageStage) {
        AgeStage.EGG -> return
        AgeStage.DEAD -> return
        AgeStage.BABY -> {
            createSingleWorker<BabyHungerWork>(
                context,
                Duration.ofMinutes(3),
                "hunger",
                ExistingWorkPolicy.REPLACE
            )
            createSingleWorker<BabyHappinessWork>(
                context,
                Duration.ofMinutes(3),
                "happiness",
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
                Duration.ofMinutes(45),
                "sleep",
                ExistingWorkPolicy.REPLACE
            )
            createSingleWorker<BabyMisbehavingWork>(
                context,
                Duration.ofMinutes(55),
                "misbehaving",
                ExistingWorkPolicy.REPLACE
            )
            createPeriodicWorker<StepCounterWorker>(
                context,
                Duration.ofSeconds(5),
                Duration.ofMinutes(15),
                "step_worker",
                policy
            )
        }

        else -> {
            val now = LocalDateTime.now()
            val midnightTonight = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusDays(1)
            val initialDelay = Duration.between(now, midnightTonight)

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
                "intelligence",
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
            createPeriodicWorker<StepGoalWork>(
                context,
                initialDelay,
                Duration.ofHours(24),
                "step_goal",
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