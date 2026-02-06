package com.example.tamagotchi.data_logging

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TamagotchiHistoryRepository(
    private val historyDao: HistoryDao
) {
    suspend fun getLatest(): TamagotchiHistory? = withContext(Dispatchers.IO) {
        Log.d("History", "Fetching latest history")
        historyDao.getAll().firstOrNull()
    }

    suspend fun storeHistory(history: TamagotchiHistory) = withContext(Dispatchers.IO) {
        Log.d("History", "Storing history: $history")
        historyDao.insertAll(history)
    }
}