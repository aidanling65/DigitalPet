package com.example.tamagotchi.main.ui

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.main.domain.logic.GameLogicManager
import com.example.tamagotchi.main.domain.workers.utils.scheduleEvolutionWork
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.utils.StepCounter
import com.example.tamagotchi.sudoku.SudokuActivity
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

    init {
        viewModelScope.launch {
            val initialState = repository.getState()
            if (initialState.initial) {
                setupNewGame()
            }
            repository.tamagotchiStateFlow.collect { state ->
                if (tamagotchiState.value.resetSteps) {
                    resetDailySteps()
                } else {
                    _tamagotchiState.value = state
                }
            }
        }

    }

    private fun updateAndSave(transform: (currentState: TamagotchiState) -> TamagotchiState) {
        viewModelScope.launch {
            repository.updateState(transform)
        }
    }

    private val stepCounter = StepCounter(context) { stepsSinceReboot ->
        updateAndSave { currentState ->
            val persistentBaseline = currentState.dailyStepBaseline

            if (persistentBaseline == null) {
                currentState.copy(
                    steps = 0,
                    dailyStepBaseline = stepsSinceReboot
                )
            } else {
                val dailySteps = stepsSinceReboot - persistentBaseline
                currentState.copy(
                    steps = dailySteps,
                    fitness = if (dailySteps > currentState.stepGoal && currentState.fitness < MAX_FITNESS) currentState.fitness + 1 else currentState.fitness
                )
            }
        }
    }

    fun startStepCounter() {
        stepCounter.startListening()
    }

    fun stopStepCounter() {
        stepCounter.stopListening()
    }

    fun resetDailySteps() {
        updateAndSave { currentState ->
            currentState.copy(
                steps = 0,
                resetSteps = false,
                dailyStepBaseline = null
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        stopStepCounter()
    }

    fun feed() { updateAndSave { gameLogicManager.feed(it) } }
    fun play() {
        val intent = Intent(context, SudokuActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
        updateAndSave { gameLogicManager.play(it) }
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