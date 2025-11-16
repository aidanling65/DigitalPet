package com.example.tamagotchi.domain.logic

import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.MAX_DISCIPLINE
import com.example.tamagotchi.data.model.MAX_HAPPINESS
import com.example.tamagotchi.data.model.MAX_HUNGER
import com.example.tamagotchi.data.model.MAX_WEIGHT
import com.example.tamagotchi.data.model.TamagotchiState

class GameLogicManager {
    private fun canInteract(current: TamagotchiState): Boolean {
        return current.ageStage != AgeStage.EGG && current.ageStage != AgeStage.DEAD && !current.sleeping
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

    fun play(current: TamagotchiState): TamagotchiState {
        if (!canInteract(current))
            return current

        return if (current.happiness < MAX_HAPPINESS) {
            current.copy(
                happiness = current.happiness.inc(),
                weight = if (current.weight > current.ageStage.minimumWeight) current.weight - 1 else current.weight
            )
        }
        else{
            current
        }
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

    fun light(current: TamagotchiState): TamagotchiState {
        return current.copy(light = !current.light)
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
        }else{
            current
        }
    }
}