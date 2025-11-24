package com.example.tamagotchi.domain.workers.utils

import android.content.Context
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.time.Duration

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