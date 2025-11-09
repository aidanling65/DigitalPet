package com.example.tamagotchi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.MAX_HAPPINESS
import com.example.tamagotchi.data.model.MAX_HUNGER
import com.example.tamagotchi.data.model.MAX_WEIGHT
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.data.model.TamagotchiState
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GameViewModel(private val repository: TamagotchiRepository) : ViewModel() {
    private val _tamagotchiState = MutableStateFlow(TamagotchiState())
    val tamagotchiState: StateFlow<TamagotchiState> = _tamagotchiState.asStateFlow()

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog.asStateFlow()

    init {
        viewModelScope.launch {
            repository.tamagotchiStateFlow.collect { state ->
                _tamagotchiState.value = state
            }
        }

        scheduleEvolutionWork(MyApp.instance, _tamagotchiState.value)
    }

    private fun saveState() {
        viewModelScope.launch {
            repository.saveState(_tamagotchiState.value)
        }
    }

    private fun canInteract(): Boolean{
        val current = _tamagotchiState.value
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
            _tamagotchiState.update {
                it.copy(
                    hunger = updatedHunger,
                    weight = updatedWeight
                )
            }
            saveState()
        }
    }

    fun play() {
        val current = tamagotchiState.value
        if(!canInteract())
            return

        if (current.happiness < MAX_HAPPINESS) {
            _tamagotchiState.update{
                it.copy(
                    happiness = current.happiness.inc(),
                    weight = if(current.weight > current.ageStage.minimumWeight) current.weight - 1 else current.weight
                )
            }
            saveState()
        }
    }

    fun clean() {
        if(!canInteract())
            return

        _tamagotchiState.update {
            it.copy(
                poop=false
            )
        }
        saveState()
    }

    fun heal() {
        val current = tamagotchiState.value
        if(!canInteract())
            return

        if (current.sick) {
            if (current.medicineTaken) {
                _tamagotchiState.update {
                    it.copy(
                        sick = false,
                        medicineTaken = false
                    )
                }
            }
            else{
                _tamagotchiState.update {
                    it.copy(
                        medicineTaken = true
                    )
                }
            }
            saveState()
        }
    }

    fun light() {
        val current = tamagotchiState.value

        _tamagotchiState.update {
            it.copy(light = !current.light)
        }
        saveState()
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (!canInteract())
            return

        if (current.misbehaving) {
            _tamagotchiState.update{
                it.copy(
                    discipline = if (current.discipline < 4) current.discipline.inc() else current.discipline,
                    misbehaving = false
                )
            }
            saveState()
        } else if (current.happiness > 0) {
            _tamagotchiState.update{
                it.copy(
                    happiness = current.happiness.dec()
                )
            }
            saveState()
        }
    }

    fun onResetClicked() {
        _showResetDialog.value = true
    }

    fun onDismissDialog() {
        _showResetDialog.value = false
    }

    fun confirmReset() {
        _tamagotchiState.update { TamagotchiState() }
        saveState()

        WorkManager.Companion.getInstance(MyApp.instance).cancelAllWork()

        scheduleEvolutionWork(MyApp.instance, _tamagotchiState.value)

        onDismissDialog()
    }
}