package com.example.tamagotchi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.savedstate.savedState
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tamagotchi.tamagotchi.AgeStage
import com.example.tamagotchi.tamagotchi.MAX_HAPPINESS
import com.example.tamagotchi.tamagotchi.MAX_HUNGER
import com.example.tamagotchi.tamagotchi.MAX_WEIGHT
import com.example.tamagotchi.tamagotchi.TamagotchiRepository
import com.example.tamagotchi.tamagotchi.TamagotchiState
import com.example.tamagotchi.workers.EvolutionWork
import com.example.tamagotchi.workers.createSingleWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration

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

        scheduleEvolutionWork()
    }

    private fun scheduleEvolutionWork(){
        val delay = _tamagotchiState.value.ageStage.stageLength ?: Duration.ofMinutes(5)
        createSingleWorker<EvolutionWork>(delay,"evolve", ExistingWorkPolicy.KEEP)
    }

    private fun saveState() {
        viewModelScope.launch {
            repository.saveState(_tamagotchiState.value)
        }
    }

    fun feed() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) return

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
        }
    }

    fun play() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.happiness < MAX_HAPPINESS) {
            _tamagotchiState.update{
                it.copy(
                    happiness = current.happiness.inc()
                )
            }
            saveState()
        }
    }

    fun clean() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        _tamagotchiState.update {
            it.copy(
                poop=false
            )
        }
        saveState()
    }

    fun heal() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
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
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD) {
            return
        }
        _tamagotchiState.update {
            it.copy(light = !current.light)
        }
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.misbehaving) {
            _tamagotchiState.update{
                it.copy(
                    discipline = if (current.discipline < 4) current.discipline.inc() else current.discipline,
                    misbehaving = false
                )
            }
        } else if (current.happiness > 0) {
            _tamagotchiState.update{
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
        _tamagotchiState.update { TamagotchiState() }
        viewModelScope.launch {
            repository.saveState(tamagotchiState.value)
        }

        WorkManager.getInstance(MyApp.instance).cancelAllWork()

        scheduleEvolutionWork()

        onDismissDialog()
    }
}