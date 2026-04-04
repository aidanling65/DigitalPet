package com.example.digitalpet.main.domain.workers.utils

import android.content.Context
import android.util.Log
import androidx.work.ExistingWorkPolicy
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.domain.workers.evolution.EvolutionWork
import java.time.Duration

fun scheduleEvolutionWork(context: Context, state: PetState){
    val delay = state.ageStage.stageLength ?: Duration.ZERO
    if(delay == Duration.ZERO){
        return
    }
    Log.d("EvolutionWork", "next evolution scheduled for in ${delay.seconds} seconds")
    createSingleWorker<EvolutionWork>(context, delay, "evolve", ExistingWorkPolicy.REPLACE)
}


/*suspend fun isWorkScheduled(context: Context, tag: String) : Boolean {
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
}*/
