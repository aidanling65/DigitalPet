package com.example.tamagotchi.main.domain.workers.evolution

import com.example.tamagotchi.main.data.model.MAX_HAPPINESS
import com.example.tamagotchi.main.data.model.MAX_HUNGER
import com.example.tamagotchi.main.data.model.PetState

fun baseEvolve(currentState: PetState): PetState {
    return  currentState.copy(
        hunger = MAX_HUNGER,
        happiness = MAX_HAPPINESS,
        discipline = 0,
        mistakes=0,
        poop = false,
        sick = false,
        misbehaving = false,
        initial = false,
        hasEvolved = true,
    )
}