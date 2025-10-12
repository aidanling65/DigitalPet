package com.example.tamagotchi

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

const val MAX_HUNGER = 4
const val MAX_WEIGHT = 99
const val MAX_HAPPINESS = 4

class GameViewModel : ViewModel() {
    private val _tamagotchiState = MutableStateFlow(TamagotchiState())
    val tamagotchiState: StateFlow<TamagotchiState> = _tamagotchiState.asStateFlow()

    private fun updateTamagotchiState(
        age: Byte = tamagotchiState.value.age,
        hunger: Byte = tamagotchiState.value.hunger,
        happiness: Byte = tamagotchiState.value.happiness,
        weight: Byte = tamagotchiState.value.weight,
        discipline: Byte = tamagotchiState.value.discipline,
        medicineTaken: Boolean = tamagotchiState.value.medicineTaken,
        sick: Boolean = tamagotchiState.value.sick,
        misbehaving: Boolean = tamagotchiState.value.misbehaving,
        poop: Boolean = tamagotchiState.value.poop,
    ){
        _tamagotchiState.update {
            it.copy(
                age = age,
                weight = weight,
                hunger = hunger,
                happiness = happiness,
                discipline = discipline,
                medicineTaken = medicineTaken,
                sick = sick,
                misbehaving = misbehaving,
                poop = poop,
            )
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
            updateTamagotchiState(hunger=updatedHunger, weight=updatedWeight)
        }
    }

    fun play(){
        val current = tamagotchiState.value
        if(current.happiness < MAX_HAPPINESS){
            updateTamagotchiState(happiness = current.happiness.inc())
        }
    }

    fun clean() {
        if (tamagotchiState.value.poop) {
            updateTamagotchiState(poop=false)
        }
    }

    fun heal(){
        val current = tamagotchiState.value
        if(current.sick){
            if(current.medicineTaken) updateTamagotchiState(sick=false, medicineTaken = false)
            else updateTamagotchiState(medicineTaken = true)
        }
    }

    fun discipline(){
        val current = tamagotchiState.value
        if(current.misbehaving){
            updateTamagotchiState(discipline = current.discipline.inc(), misbehaving  = false)
        }
        else{
            updateTamagotchiState(happiness = current.happiness.dec())
        }
    }
}