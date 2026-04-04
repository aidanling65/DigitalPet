package com.example.digitalpet.main.domain.workers.baby

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.repository.PetRepository


class BabyBehaveWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        repository.updateState {
            it.copy(
                misbehaving = false
            )
        }
        return Result.success()
    }
}