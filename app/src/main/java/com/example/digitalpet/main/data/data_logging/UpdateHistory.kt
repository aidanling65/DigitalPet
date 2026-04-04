package com.example.digitalpet.main.data.data_logging

import android.content.Context
import java.time.LocalDateTime


suspend fun updateHistory(context: Context, transform: (PassiveHistory) -> PassiveHistory){
    val historyDb = PetDatabase.getDatabase(context = context)
    val historyRepository = PetHistoryRepository(historyDb.historyDao())

    val previous = historyRepository.getLatestPassive() ?: PassiveHistory()
    val updated = transform(previous.copy(id = 0, string = LocalDateTime.now().toString()))
    historyRepository.storeHistory(updated)
}

suspend fun storeEvolution(context: Context, evolution: EvolutionLog){
    val historyDb = PetDatabase.getDatabase(context = context)
    val historyRepository = PetHistoryRepository(historyDb.historyDao())
    historyRepository.storeEvolution(evolution)
}