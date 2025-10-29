package com.example.tamagotchi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

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
        val current = tamagotchiState.value
        if(current.light) {
            updateTamagotchiState(light =false)
            if(current.sleeping){
                current.currentAnimation = current.animations.lights_out_sleep ?: current.animations.idle
            }
            else{
                current.currentAnimation = current.animations.lights_out_awake ?: current.animations.idle
            }
        }
        else{
            updateTamagotchiState(light=true)
            if(current.sleeping){
                current.currentAnimation = current.animations.sleep ?: current.animations.idle
            }
            else{
                current.currentAnimation = current.animations.idle
            }
        }
    }

    fun discipline() {
        val current = tamagotchiState.value
        if (current.misbehaving) {
            updateTamagotchiState(discipline = current.discipline.inc(), misbehaving = false)
        } else if (current.happiness > 0) {
            updateTamagotchiState(happiness = current.happiness.dec())
        }
    }

    fun reset(){
        _tamagotchiState.update { TamagotchiState() }
        viewModelScope.launch {
            repository.saveState(tamagotchiState.value)
        }

        val evolutionRequest = OneTimeWorkRequestBuilder<EvolutionWork>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(MyApp.instance).enqueueUniqueWork(
            "evolve",
            ExistingWorkPolicy.KEEP,
            evolutionRequest
        )
    }
}