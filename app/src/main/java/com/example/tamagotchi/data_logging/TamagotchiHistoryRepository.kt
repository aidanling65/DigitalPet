package com.example.tamagotchi.data_logging

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TamagotchiHistoryRepository(
    private val historyDao: HistoryDao
) {
    suspend fun getLatestActive(): ActiveHistory? = withContext(Dispatchers.IO) {
        Log.d("History", "Fetching latest history")
        historyDao.getLatestActive()
    }

    suspend fun getLatestPassive(): PassiveHistory? = withContext(Dispatchers.IO) {
        Log.d("History", "Fetching latest history")
        historyDao.getLatestPassive()
    }

    suspend fun storeHistory(history: ActiveHistory) = withContext(Dispatchers.IO) {
        Log.d("History", "Storing history: $history")
        historyDao.insert(history)
    }

    suspend fun storeHistory(history: PassiveHistory) = withContext(Dispatchers.IO) {
        Log.d("History", "Storing history: $history")
        historyDao.insert(history)
    }
}