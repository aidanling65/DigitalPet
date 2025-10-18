package com.example.tamagotchi

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.ui.Modifier

@RequiresApi(Build.VERSION_CODES.O)
fun baseEvolve(currentState: TamagotchiState) : TamagotchiState{
    return currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun eggBabyEvolve(currentState: TamagotchiState): TamagotchiState {
    Log.d("EvolutionWork", "Your Tamagotchi is hatching!")
    val updatedState = baseEvolve(currentState)
    return updatedState.copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun babyChildEvolve(currentState: TamagotchiState): TamagotchiState {
    Log.d("EvolutionWork", "Your Tamagotchi has evolved!")
    val updatedState = baseEvolve(currentState)
    return updatedState.copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun childTeenEvolve(currentState: TamagotchiState): TamagotchiState {
    Log.d("msg", "Your Tamagotchi has evolved!")
    val updatedState = baseEvolve(currentState)
    return updatedState.copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun teenAdultEvolve(currentState: TamagotchiState): TamagotchiState {
    val discipline = currentState.discipline
    val mistakes = currentState.mentalMistakes + currentState.physicalMistakes

    val nextAnimation = when(currentState.animations){
        EvolutionAnimations.TEEN_1 -> when{
            discipline == MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_1
            discipline == MAX_DISCIPLINE && mistakes > 2 -> EvolutionAnimations.ADULT_2

            discipline <= MAX_DISCIPLINE / 2 && mistakes <= 2 -> EvolutionAnimations.ADULT_3
            discipline <= MAX_DISCIPLINE / 2 && mistakes > 2 -> EvolutionAnimations.ADULT_6

            discipline > MAX_DISCIPLINE / 2 && discipline < MAX_DISCIPLINE && mistakes <= 2 -> EvolutionAnimations.ADULT_2
            else -> EvolutionAnimations.ADULT_5
        }
        else -> when{
            discipline == MAX_DISCIPLINE -> EvolutionAnimations.ADULT_4
            discipline <= MAX_DISCIPLINE / 2 -> EvolutionAnimations.ADULT_5
            else -> EvolutionAnimations.ADULT_6
        }
    }

    Log.d("msg", "Your Tamagotchi has evolved!")
    val updatedState = baseEvolve(currentState)
    return updatedState.copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations =  nextAnimation
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun adultDeadEvolve(currentState: TamagotchiState): TamagotchiState {
    Log.d("msg", "Your Tamagotchi has died!")
    return currentState.copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
}