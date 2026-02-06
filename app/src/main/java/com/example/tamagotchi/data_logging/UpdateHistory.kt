package com.example.tamagotchi.data_logging

import android.content.Context


suspend fun updateHistory(context: Context, transform: (TamagotchiHistory) -> TamagotchiHistory){
    val historyDb = TamagotchiDatabase.getDatabase(context = context)
    val historyRepository = TamagotchiHistoryRepository(historyDb.historyDao())

    val previous = historyRepository.getLatest() ?: TamagotchiHistory()
    val updated = transform(previous.copy(id = 0))
    historyRepository.storeHistory(updated)
}