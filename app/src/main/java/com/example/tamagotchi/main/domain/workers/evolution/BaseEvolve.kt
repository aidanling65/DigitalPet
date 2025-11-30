package com.example.tamagotchi.main.domain.workers.evolution

import com.example.tamagotchi.main.data.model.TamagotchiState

fun baseEvolve(currentState: TamagotchiState): TamagotchiState {
    return  currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0,
        poop = false,
        sick = false,
        misbehaving = false,
        initial = false
    )
}