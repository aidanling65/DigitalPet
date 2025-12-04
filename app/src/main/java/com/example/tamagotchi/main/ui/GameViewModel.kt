package com.example.tamagotchi.main.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.logic.GameLogicManager
import com.example.tamagotchi.main.domain.workers.utils.scheduleEvolutionWork
import com.example.tamagotchi.step_tracker.repository.StepDatabase
import com.example.tamagotchi.step_tracker.repository.StepRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(private val context: Context, private val repository: TamagotchiRepository) :
    ViewModel() {
    private val _tamagotchiState = MutableStateFlow(TamagotchiState())
    val tamagotchiState: StateFlow<TamagotchiState> = _tamagotchiState.asStateFlow()

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog.asStateFlow()

    private val gameLogicManager = GameLogicManager()
    private val stepDb = StepDatabase.getDatabase(context)
    private val stepRepository = StepRepository(stepDb.stepsDao())

    init {
        viewModelScope.launch {
            val initialState = repository.getState()
            if (initialState.initial) {
                setupNewGame()
            }
            repository.tamagotchiStateFlow.collect { state ->
                _tamagotchiState.value = state.copy()
            }
        }

        viewModelScope.launch {
            stepRepository.loadTodaysSteps().collect { steps ->
                Log.d("Steps", "Loaded steps: $steps")
                updateAndSave { it.copy(steps=steps.toInt()) }
            }
        }
    }

    private fun updateAndSave(transform: (currentState: TamagotchiState) -> TamagotchiState) {
        viewModelScope.launch {
            repository.updateState(transform)
        }
    }

    fun feed() { updateAndSave { gameLogicManager.feed(it) } }
    fun play() {
        updateAndSave { gameLogicManager.play(it) }
    }
    fun learning(){
        updateAndSave { gameLogicManager.learning(it) }
    }
    fun clean() { updateAndSave { gameLogicManager.clean(it) }}
    fun heal() { updateAndSave { gameLogicManager.heal(it) } }
    fun light() { updateAndSave { gameLogicManager.light(it) } }
    fun discipline() { updateAndSave { gameLogicManager.discipline(it) } }
    
    fun onResetClicked() { _showResetDialog.value = true }

    fun onDismissDialog() {
        _showResetDialog.value = false
    }

    fun confirmReset() {
        setupNewGame()
        onDismissDialog()
    }

    fun setupNewGame() {
        WorkManager.getInstance(context).cancelAllWork()
        viewModelScope.launch {
            val resetState = TamagotchiState(initial = false)
            repository.saveState(resetState)
            scheduleEvolutionWork(context, resetState)
        }
    }
}