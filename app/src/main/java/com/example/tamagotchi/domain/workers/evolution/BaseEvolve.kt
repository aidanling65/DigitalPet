package com.example.tamagotchi.domain.workers.evolution

import com.example.tamagotchi.data.model.TamagotchiState


fun baseEvolve(currentState: TamagotchiState): TamagotchiState {
    return  currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0,
        poop = false,
        sick = false
    )
}