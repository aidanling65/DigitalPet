package com.example.digitalpet.main.domain.workers.periodic


import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.digitalpet.main.data.data_logging.updateHistory
import com.example.digitalpet.main.data.repository.PetRepository

class StepGoalWork(
    appContext: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(appContext, workerParams) {
    private val repository = PetRepository(appContext)

    override suspend fun doWork(): Result {
        var hit = false
        repository.updateState {
            hit=it.stepGoalHit
            it.copy(
                stepGoalHit = false,
                stepGoal2Hit = false
            )
        }

        if(hit){
            updateHistory(applicationContext) {
                it.copy(stepGoalHit = it.stepGoalHit + 1)
            }
        } else{
            updateHistory(applicationContext) {
                it.copy(stepGoalMissed = it.stepGoalMissed + 1)
            }
        }


        return Result.success()
    }
}