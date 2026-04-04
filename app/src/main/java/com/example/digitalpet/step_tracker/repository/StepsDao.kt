package com.example.digitalpet.step_tracker.repository

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface StepsDao {
    @Query("SELECT * FROM steps")
    suspend fun getAll(): List<StepCount>

    @Query("SELECT * FROM steps WHERE created_at >= :startDateTime " +
            "AND created_at < date(:startDateTime, '+1 day')")
    fun loadAllStepsFromToday(startDateTime: String): Flow<List<StepCount>>

    @Insert
    suspend fun insertAll(vararg steps: StepCount)

    @Delete
    suspend fun delete(steps: StepCount)
}