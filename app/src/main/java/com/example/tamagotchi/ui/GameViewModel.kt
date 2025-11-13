package com.example.tamagotchi.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.MAX_HAPPINESS
import com.example.tamagotchi.data.model.MAX_HUNGER
import com.example.tamagotchi.data.model.MAX_WEIGHT
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.utils.StepCounter
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

    init {
        viewModelScope.launch {
            val initialState = repository.getState()
            if(initialState.initial){
                setupNewGame()
            }
            repository.tamagotchiStateFlow.collect { state ->
                if (tamagotchiState.value.resetSteps) {
                    resetDailySteps()
                } else{
                    _tamagotchiState.value = state
                }
            }
        }

    }

    private fun updateAndSave(transform: (currentState: TamagotchiState) -> TamagotchiState) {
        viewModelScope.launch {
            val currentState = repository.getState()
            val newState = transform(currentState)
            repository.saveState(newState)
        }
    }

    private var dailyStepBaseline: Int? = null
    private val stepCounter = StepCounter(context) { steps ->
        if (dailyStepBaseline == null) {
            dailyStepBaseline = steps - tamagotchiState.value.steps
        }
        val dailySteps = steps - (dailyStepBaseline!!)
        updateAndSave {
            it.copy(
                steps = dailySteps,
            )
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
                resetSteps = false
            )
        }
        dailyStepBaseline = null
    }

    override fun onCleared() {
        super.onCleared()
        stopStepCounter()
    }

    private fun canInteract(): Boolean {
        val current = tamagotchiState.value
        return current.ageStage != AgeStage.EGG && current.ageStage != AgeStage.DEAD && !current.sleeping
    }

    fun feed() {
        val current = tamagotchiState.value
        if (!canInteract())
            return

        if (current.hunger < MAX_HUNGER) {
            val updatedHunger = current.hunger.inc()
            var updatedWeight = current.weight
            if (current.weight < MAX_WEIGHT) {
                updatedWeight++
            }
            updateAndSave {
                it.copy(
                    hunger = updatedHunger,
                    weight = updatedWeight
                )
            }
        }
    }

    fun play() {
        val current = tamagotchiState.value
        if (!canInteract())
            return

        if (current.happiness < MAX_HAPPINESS) {
            updateAndSave {
                it.copy(
                    happiness = current.happiness.inc(),
                    weight = if (current.weight > current.ageStage.minimumWeight) current.weight - 1 else current.weight
                )
            }
        }
    }

    fun clean() {
        if (!canInteract())
            return

        updateAndSave {
            it.copy(
                poop = false
            )
        }
    }

    fun heal() {
        val current = tamagotchiState.value
        if (!canInteract())
            return

        if (current.sick) {
            if (current.medicineTaken) {
                updateAndSave {
                    it.copy(
                        sick = false,
                        medicineTaken = false
                    )
                }
            } else {
                updateAndSave {
                    it.copy(
                        medicineTaken = true
                    )
                }
            }
        }
    }

    fun light() {
        val current = tamagotchiState.value

        updateAndSave {
            it.copy(
                light = !current.light
            )
        }
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (!canInteract())
            return

        if (current.misbehaving) {
            updateAndSave {
                it.copy(
                    discipline = if (current.discipline < 4) current.discipline.inc() else current.discipline,
                    misbehaving = false
                )
            }
        } else if (current.happiness > 0) {
            updateAndSave {
                it.copy(
                    happiness = current.happiness.dec()
                )
            }
        }
    }

    fun onResetClicked() {
        _showResetDialog.value = true
    }

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