package com.example.tamagotchi

fun baseEvolve(currentState: TamagotchiState) : TamagotchiState{
    val updatedState =  currentState.copy(
        hunger = 0,
        happiness = 0,
        discipline = 0,
        mentalMistakes = 0,
        physicalMistakes = 0,
        poop = false,
        sick = false
    )
    return  updatedState
}

fun eggBabyEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has hatched!")

    val updatedState = currentState.copy(
        ageStage = AgeStage.BABY,
        weight = AgeStage.BABY.minimumWeight,
        animations = EvolutionAnimations.BABY,
    )
    return baseEvolve(updatedState)
}

fun babyChildEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has evolved!")

    val updatedState =  currentState.copy(
        ageStage = AgeStage.CHILD,
        weight = AgeStage.CHILD.minimumWeight,
        animations = EvolutionAnimations.CHILD,
    )
    return baseEvolve(updatedState)
}

fun childTeenEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has evolved!")

    val updatedState = currentState.copy(
        ageStage = AgeStage.TEEN,
        weight = AgeStage.TEEN.minimumWeight,
        animations = if (currentState.physicalMistakes + currentState.mentalMistakes <= 1) EvolutionAnimations.TEEN_1 else EvolutionAnimations.TEEN_2,
    )
    return  baseEvolve(updatedState)
}

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

    showNotification("Your Tamagotchi has evolved!")

    val updatedState = currentState.copy(
        ageStage = AgeStage.ADULT,
        weight = AgeStage.ADULT.minimumWeight,
        animations =  nextAnimation
    )

    return baseEvolve(updatedState)
}

fun deadEvolve(currentState: TamagotchiState): TamagotchiState {
    showNotification("Your Tamagotchi has died!")
    val updatedState = currentState.copy(
        ageStage = AgeStage.DEAD,
        weight = AgeStage.DEAD.minimumWeight,
        animations = EvolutionAnimations.DEAD
    )
    return updatedState
}