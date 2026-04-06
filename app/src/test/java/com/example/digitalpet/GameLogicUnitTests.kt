package com.example.digitalpet

import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.domain.logic.GameLogicManager
import org.junit.Test
import org.junit.Assert.assertEquals

class GameLogicManager {
    val gameLogicManager = GameLogicManager()

    @Test
    fun testFeed() {
        val petState = PetState(ageStage = AgeStage.BABY, hunger=0)
        val updatedState = gameLogicManager.feed(petState)

        assertEquals(1, updatedState.hunger)
    }

    @Test
    fun testPlayLoss() {
        val petState = PetState(ageStage = AgeStage.BABY,happiness = 1)
        val updatedState = gameLogicManager.play(petState, 0)
        assertEquals(0, updatedState.happiness)
    }

    @Test
    fun testPlayNeutral(){
        val petState = PetState(ageStage = AgeStage.BABY,happiness = 1)
        val updatedState = gameLogicManager.play(petState, 50)
        assertEquals(1, updatedState.happiness)
    }

    @Test
    fun testPlayWin100(){
        val petState = PetState(ageStage = AgeStage.BABY,happiness = 1)
        val updatedState = gameLogicManager.play(petState, 100)
        assertEquals(2, updatedState.happiness)
    }

    @Test
    fun testPlayWin150(){
        val petState = PetState(ageStage = AgeStage.BABY,happiness = 1)
        val updatedState = gameLogicManager.play(petState, 150)
        assertEquals(3, updatedState.happiness)
    }

    @Test
    fun testPlayWin200(){
        val petState = PetState(ageStage = AgeStage.BABY,happiness = 1)
        val updatedState = gameLogicManager.play(petState, 200)
        assertEquals(4, updatedState.happiness)
    }

    @Test
    fun testClean() {
        val petState = PetState(ageStage = AgeStage.BABY, poop = true)
        val updatedState = gameLogicManager.clean(petState)
        assertEquals(false, updatedState.poop)
    }

    @Test
    fun testSick(){
        val petState = PetState(ageStage = AgeStage.BABY, sick = true)
        val updatedState = gameLogicManager.heal(petState)
        assertEquals(false, updatedState.sick)

    }

    @Test
    fun testDisciplineCorrect(){
        val petState = PetState(ageStage = AgeStage.BABY, discipline = 0, misbehaving = true)
        val updatedState = gameLogicManager.discipline(petState)
        assertEquals(1, updatedState.discipline)
    }

    @Test
    fun testDisciplineIncorrect(){
        val petState = PetState(ageStage = AgeStage.BABY, discipline = 0, misbehaving = false, happiness = 2)
        val updatedState = gameLogicManager.discipline(petState)
        assertEquals(0, updatedState.discipline)
        assertEquals(1, updatedState.happiness)
    }
}

