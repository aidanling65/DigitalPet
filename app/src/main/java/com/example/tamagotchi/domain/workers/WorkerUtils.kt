package com.example.tamagotchi.domain.workers

import android.content.Context
import android.util.Log
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.evolution.EvolutionWork
import java.time.Duration

fun scheduleEvolutionWork(context: Context, state: TamagotchiState){
    val delay = state.ageStage.stageLength ?: Duration.ZERO
    if(delay == Duration.ZERO){
        return
    }
    Log.d("EvolutionWork", "next evolution scheduled for in ${delay.seconds}")
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