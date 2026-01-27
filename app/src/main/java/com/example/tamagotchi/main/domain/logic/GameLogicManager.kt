package com.example.tamagotchi.main.domain.logic

import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.main.data.model.MAX_HAPPINESS
import com.example.tamagotchi.main.data.model.MAX_HUNGER
import com.example.tamagotchi.main.data.model.MAX_INTELLIGENCE
import com.example.tamagotchi.main.data.model.MAX_WEIGHT
import com.example.tamagotchi.main.data.model.TamagotchiState

class GameLogicManager {
    private fun canInteract(current: TamagotchiState): Boolean {
        return current.ageStage != AgeStage.EGG && current.ageStage != AgeStage.DEAD && !current.sleeping && !current.paused
    }

    fun feed(current: TamagotchiState): TamagotchiState {
        if (!canInteract(current))
            return current

        if (current.hunger < MAX_HUNGER) {
            val updatedHunger = current.hunger.inc()
            var updatedWeight = current.weight
            if (current.weight < MAX_WEIGHT) {
                updatedWeight++
            }
            return current.copy(
                hunger = updatedHunger,
                weight = updatedWeight
            )
        }

        return current
    }

    fun play(current: TamagotchiState, score: Int): TamagotchiState {
        val happinessGain = when {
            score < 50 ->(-1)
            score < 100 -> 0
            score < 150 -> 1
            score < 200 -> 2
            else -> 3
        }

        return current.copy(
            happiness = (current.happiness + happinessGain)
                .coerceAtMost(MAX_HAPPINESS)
                .coerceAtLeast(0),
            weight = if (current.weight > current.ageStage.minimumWeight) current.weight - 1 else current.weight
        )
    }

    fun clean(current: TamagotchiState): TamagotchiState {
        if (!canInteract(current))
            return current

        return current.copy(
            poop = false
        )
    }

    fun heal(current: TamagotchiState): TamagotchiState {
        if (!canInteract(current) || !current.sick)
            return current

        return if (current.medicineTaken) {
            current.copy(
                sick = false,
                medicineTaken = false
            )
        } else {
            current.copy(
                medicineTaken = true
            )
        }
    }

    fun discipline(current: TamagotchiState): TamagotchiState {
        if (!canInteract(current))
            return current

        return if (current.misbehaving) {
            current.copy(
                discipline = if (current.discipline < MAX_DISCIPLINE) current.discipline.inc() else current.discipline,
                misbehaving = false
            )
        } else if (current.happiness > 0) {
            current.copy(
                happiness = current.happiness - 1
            )
        } else {
            current
        }
    }

    fun learning(current: TamagotchiState): TamagotchiState {
        val updatedIntelligence = (current.intelligence + 1).coerceAtMost(MAX_INTELLIGENCE)
        return current.copy(
            intelligence = updatedIntelligence,
            mentalMistakes = if(updatedIntelligence == MAX_INTELLIGENCE) (current.mentalMistakes - 1).coerceAtLeast(0) else current.mentalMistakes
        )
    }
}