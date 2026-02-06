package com.example.tamagotchi.data_logging

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM tamagotchi_active_history ORDER BY game_opened DESC LIMIT 1")
    suspend fun getLatestActive(): ActiveHistory?

    @Query("SELECT * FROM tamagotchi_passive_history ORDER BY created_at DESC LIMIT 1")
    suspend fun getLatestPassive(): PassiveHistory?

    @Insert
    suspend fun insert(tamagotchiInstance: ActiveHistory)

    @Insert
    suspend fun insert(tamagotchiInstance: PassiveHistory)

    @Delete
    suspend fun delete(tamagotchiInstance: ActiveHistory)

    @Delete
    suspend fun delete(tamagotchiInstance: PassiveHistory)

}