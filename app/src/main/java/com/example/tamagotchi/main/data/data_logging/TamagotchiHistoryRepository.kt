package com.example.tamagotchi.main.data.data_logging

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

    suspend fun storeEvolution(evolution: EvolutionLog) = withContext(Dispatchers.IO) {

        Log.d("History", "Storing evolution: $evolution")
        historyDao.insert(evolution)
    }

    suspend fun updateHistory(history: PassiveHistory) = withContext(Dispatchers.IO) {
        Log.d("History", "Updating history: $history")
        historyDao.update(history)
    }

    suspend fun storeSession(session: SessionLog): Long = withContext(Dispatchers.IO) {
        Log.d("History", "Storing session: $session")
        historyDao.insert(session)
    }
}