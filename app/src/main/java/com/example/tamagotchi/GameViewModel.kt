package com.example.tamagotchi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    }

    private fun updateTamagotchiState(
        age: Int = tamagotchiState.value.age,
        hunger: Int = tamagotchiState.value.hunger,
        happiness: Int = tamagotchiState.value.happiness,
        weight: Int = tamagotchiState.value.weight,
        discipline: Int = tamagotchiState.value.discipline,
        light: Boolean = tamagotchiState.value.light,
        medicineTaken: Boolean = tamagotchiState.value.medicineTaken,
        sick: Boolean = tamagotchiState.value.sick,
        misbehaving: Boolean = tamagotchiState.value.misbehaving,
        poop: Boolean = tamagotchiState.value.poop,
    ) {
        _tamagotchiState.update {
            it.copy(
                age = age,
                weight = weight,
                hunger = hunger,
                happiness = happiness,
                discipline = discipline,
                light = light,
                medicineTaken = medicineTaken,
                sick = sick,
                misbehaving = misbehaving,
                poop = poop,
            )
        }
        viewModelScope.launch {
            repository.saveState(tamagotchiState.value)
        }
    }

    fun feed() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.hunger < MAX_HUNGER) {
            val updatedHunger = current.hunger.inc()
            var updatedWeight = current.weight
            if (current.weight < MAX_WEIGHT) {
                updatedWeight++
            }
            updateTamagotchiState(hunger = updatedHunger, weight = updatedWeight)
        }
    }

    fun play() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.happiness < MAX_HAPPINESS) {
            updateTamagotchiState(happiness = current.happiness.inc())
        }
    }

    fun clean() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        updateTamagotchiState(poop = false)
    }

    fun heal() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.sick) {
            if (current.medicineTaken) updateTamagotchiState(sick = false, medicineTaken = false)
            else updateTamagotchiState(medicineTaken = true)
        }
    }

    fun light() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD) {
            return
        }
        updateTamagotchiState(light = !current.light)
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (current.ageStage == AgeStage.EGG || current.ageStage == AgeStage.DEAD || current.sleeping) {
            return
        }
        if (current.misbehaving) {
            updateTamagotchiState(
                discipline = if (current.discipline < 4) current.discipline.inc() else current.discipline,
                misbehaving = false
            )
        } else if (current.happiness > 0) {
            updateTamagotchiState(happiness = current.happiness.dec())
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

        val evolutionRequest = OneTimeWorkRequestBuilder<EvolutionWork>()
            .setInitialDelay(tamagotchiState.value.ageStage.stageLength ?: Duration.ofMinutes(5))
            .build()

        WorkManager.getInstance(MyApp.instance).enqueueUniqueWork(
            "evolve",
            ExistingWorkPolicy.REPLACE,
            evolutionRequest
        )

        onDismissDialog()
    }
}