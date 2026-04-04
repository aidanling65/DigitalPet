package com.example.digitalpet.main.data.data_logging

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryDao {

    @Query("SELECT * FROM active_history ORDER BY id DESC LIMIT 1")
    suspend fun getLatestActive(): ActiveHistory?

    @Query("SELECT * FROM passive_history ORDER BY created_at DESC LIMIT 1")
    suspend fun getLatestPassive(): PassiveHistory?

    @Query("SELECT * FROM evolution_log ORDER BY evolution_time DESC LIMIT 1")
    suspend fun getLatestEvolution(): EvolutionLog?

    @Update
    suspend fun update(petInstance: PassiveHistory)

    @Insert
    suspend fun insert(petInstance: ActiveHistory)

    @Insert
    suspend fun insert(petInstance: EvolutionLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(petInstance: SessionLog) : Long

    @Insert
    suspend fun insert(petInstance: PassiveHistory)

    @Delete
    suspend fun delete(petInstance: ActiveHistory)

    @Delete
    suspend fun delete(petInstance: PassiveHistory)

}