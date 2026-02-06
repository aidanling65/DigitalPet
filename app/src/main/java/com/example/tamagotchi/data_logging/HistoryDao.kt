package com.example.tamagotchi.data_logging

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface HistoryDao {

    @Query("SELECT * FROM tamagotchi_history ORDER BY created_at DESC LIMIT 1")
    suspend fun getAll(): List<TamagotchiHistory>

    @Insert
    suspend fun insertAll(vararg tamagotchiInstances: TamagotchiHistory)

    @Delete
    suspend fun delete(tamagotchiInstance: TamagotchiHistory)
}