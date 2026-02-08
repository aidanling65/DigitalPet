package com.example.tamagotchi.main.data.data_logging

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface HistoryDao {

    @Query("SELECT * FROM tamagotchi_active_history ORDER BY id DESC LIMIT 1")
    suspend fun getLatestActive(): ActiveHistory?

    @Query("SELECT * FROM tamagotchi_passive_history ORDER BY created_at DESC LIMIT 1")
    suspend fun getLatestPassive(): PassiveHistory?

    @Query("SELECT * FROM evolution_log ORDER BY evolution_time DESC LIMIT 1")
    suspend fun getLatestEvolution(): EvolutionLog?

    @Update
    suspend fun update(tamagotchiInstance: PassiveHistory)

    @Insert
    suspend fun insert(tamagotchiInstance: ActiveHistory)

    @Insert
    suspend fun insert(tamagotchiInstance: EvolutionLog)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tamagotchiInstance: SessionLog) : Long

    @Insert
    suspend fun insert(tamagotchiInstance: PassiveHistory)

    @Delete
    suspend fun delete(tamagotchiInstance: ActiveHistory)

    @Delete
    suspend fun delete(tamagotchiInstance: PassiveHistory)

}