package com.example.tamagotchi.workers

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.ListenableWorker
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tamagotchi.MyApp
import java.time.Duration

inline fun <reified T: ListenableWorker> createSingleWorker(
    delay: Duration,
    tag: String,
    policy: ExistingWorkPolicy
){
    val request = OneTimeWorkRequestBuilder<T>()
        .setInitialDelay(delay)
        .build()

    WorkManager.Companion.getInstance(MyApp.instance).enqueueUniqueWork(
        tag,
        policy,
        request
    )
}

inline fun <reified T: ListenableWorker> createPeriodicWorker(
    initialDelay: Duration,
    periodicDelay: Duration,
    tag: String,
    policy: ExistingPeriodicWorkPolicy
){

    val periodicWorkRequest = PeriodicWorkRequestBuilder<T>(periodicDelay)
        .setInitialDelay(initialDelay)
        .build()

    WorkManager.getInstance(MyApp.instance).enqueueUniquePeriodicWork(
        tag,
        policy,
        periodicWorkRequest
    )
}