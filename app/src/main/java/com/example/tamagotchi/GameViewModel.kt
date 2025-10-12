package com.example.tamagotchi

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

const val MAX_HUNGER = 10
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4

class GameViewModel(private val repository: TamagotchiRepository) : ViewModel() {
    private val _tamagotchiState = MutableStateFlow(TamagotchiState())
    val tamagotchiState: StateFlow<TamagotchiState> = _tamagotchiState.asStateFlow()

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
        if (current.happiness < MAX_HAPPINESS) {
            updateTamagotchiState(happiness = current.happiness.inc())
        }
    }

    fun clean() {
        if (tamagotchiState.value.poop) {
            updateTamagotchiState(poop = false)
        }
    }

    fun heal() {
        val current = tamagotchiState.value
        if (current.sick) {
            if (current.medicineTaken) updateTamagotchiState(sick = false, medicineTaken = false)
            else updateTamagotchiState(medicineTaken = true)
        }
    }

    fun light() {
        updateTamagotchiState(light = !tamagotchiState.value.light)
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (current.misbehaving) {
            updateTamagotchiState(discipline = current.discipline.inc(), misbehaving = false)
        } else {
            updateTamagotchiState(happiness = current.happiness.dec())
        }
    }
}