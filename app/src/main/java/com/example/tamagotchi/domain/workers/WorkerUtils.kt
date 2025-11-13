package com.example.tamagotchi.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.baby.BabyHungerHappinessWork
import com.example.tamagotchi.domain.workers.baby.BabyPoopWork
import com.example.tamagotchi.domain.workers.baby.BabySickWork
import com.example.tamagotchi.domain.workers.baby.BabySleepWork
import com.example.tamagotchi.domain.workers.evolution.EvolutionWork
import com.example.tamagotchi.domain.workers.regular.DeathWork
import com.example.tamagotchi.domain.workers.regular.HappinessDecayWork
import com.example.tamagotchi.domain.workers.regular.HungerDecayWork
import com.example.tamagotchi.domain.workers.regular.MisbehavingWork
import com.example.tamagotchi.domain.workers.regular.PoopWork
import com.example.tamagotchi.domain.workers.regular.SickWork
import com.example.tamagotchi.domain.workers.regular.SleepWork
import com.example.tamagotchi.domain.workers.regular.StepWork
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import java.time.LocalTime
import java.time.ZoneId

fun scheduleEvolutionWork(context: Context, state: TamagotchiState){
    val delay = state.ageStage.stageLength ?: Duration.ZERO
    if(delay == Duration.ZERO){
        return
    }
    Log.d("EvolutionWork", "next evolution scheduled for in ${delay.seconds} seconds")
    createSingleWorker<EvolutionWork>(context, delay, "evolve", ExistingWorkPolicy.REPLACE)
}

inline fun <reified T: ListenableWorker> createSingleWorker(
    context: Context,
    delay: Duration,
    tag: String,
    policy: ExistingWorkPolicy,
    data: Data? = null
){
    var builder = OneTimeWorkRequestBuilder<T>()
        .setInitialDelay(delay)

    if(data != null){
        builder.setInputData(data)
    }

    val request = builder.build()

    WorkManager.Companion.getInstance(context).enqueueUniqueWork(
        tag,
        policy,
        request
    )
}

inline fun <reified T: ListenableWorker> createPeriodicWorker(
    context: Context,
    initialDelay: Duration,
    periodicDelay: Duration,
    tag: String,
    policy: ExistingPeriodicWorkPolicy
){

    val periodicWorkRequest = PeriodicWorkRequestBuilder<T>(periodicDelay)
        .setInitialDelay(initialDelay)
        .build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
        tag,
        policy,
        periodicWorkRequest
    )
}

suspend fun isWorkScheduled(context: Context, tag: String) : Boolean {
    val workManager = WorkManager.getInstance(context)

    return withContext(Dispatchers.IO){
        try {
            val workInfos = workManager.getWorkInfosForUniqueWork(tag).get()

            workInfos?.any {
                it.state == WorkInfo.State.ENQUEUED || it.state == WorkInfo.State.RUNNING
            } ?: false
        } catch (e: Exception){
            e.printStackTrace()
            false
        }
    }
}

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
        }
    }
}