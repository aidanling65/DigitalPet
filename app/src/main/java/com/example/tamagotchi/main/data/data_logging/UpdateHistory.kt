package com.example.tamagotchi.main.data.data_logging

import android.content.Context
import java.time.LocalDateTime


suspend fun updateHistory(context: Context, transform: (PassiveHistory) -> PassiveHistory){
    val historyDb = TamagotchiDatabase.getDatabase(context = context)
    val historyRepository = TamagotchiHistoryRepository(historyDb.historyDao())

    val previous = historyRepository.getLatestPassive() ?: PassiveHistory()
    val updated = transform(previous.copy(id = 0, string = LocalDateTime.now().toString()))
    historyRepository.storeHistory(updated)
}

suspend fun storeEvolution(context: Context, evolution: EvolutionLog){
    val historyDb = TamagotchiDatabase.getDatabase(context = context)
    val historyRepository = TamagotchiHistoryRepository(historyDb.historyDao())
    historyRepository.storeEvolution(evolution)
}