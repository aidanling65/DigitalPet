package com.example.digitalpet

import com.example.digitalpet.main.data.model.AgeStage
import com.example.digitalpet.main.data.model.EvolutionAnimations
import com.example.digitalpet.main.data.model.MAX_DISCIPLINE
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.domain.workers.evolution.babyChildEvolve
import com.example.digitalpet.main.domain.workers.evolution.childTeenEvolve
import com.example.digitalpet.main.domain.workers.evolution.eggBabyEvolve
import com.example.digitalpet.main.domain.workers.evolution.teenAdultEvolve
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

class EvolutionUnitTests {
    @Test
    fun eggBabyEvolveTest(){

        val eggState = PetState()
        val (newState, evolutionLog) = eggBabyEvolve(eggState)
        assertEquals(AgeStage.BABY, newState.ageStage)
        assertEquals(AgeStage.BABY, evolutionLog.ageStage)

        assertEquals(EvolutionAnimations.BABY, newState.animations)
        assertEquals(EvolutionAnimations.BABY, evolutionLog.evolutionType)
    }

    @Test
    fun babyChildEvolveTest(){
        val babyState = PetState(ageStage = AgeStage.BABY, animations = EvolutionAnimations.BABY)
        val (newState, _) = babyChildEvolve(babyState)

        assertEquals(AgeStage.CHILD, newState.ageStage)

        assertEquals(EvolutionAnimations.CHILD, newState.animations)
    }

    @Test
    fun childTeen1EvolveTest(){
        val childState = PetState(ageStage = AgeStage.CHILD, animations = EvolutionAnimations.CHILD)
        val (newState, _) = childTeenEvolve(childState)

        assertEquals(AgeStage.TEEN, newState.ageStage)

        assertEquals(EvolutionAnimations.TEEN_1, newState.animations)
    }

    @Test
    fun childTeen2EvolveTest(){
        val childState = PetState(ageStage = AgeStage.CHILD, animations = EvolutionAnimations.CHILD, mistakes = 2)
        val (newState, _) = childTeenEvolve(childState)

        assertEquals(AgeStage.TEEN, newState.ageStage)

        assertEquals(EvolutionAnimations.TEEN_2, newState.animations)
    }

    @Test
    fun teenAdult1Evolve(){

        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_1, discipline = MAX_DISCIPLINE, mistakes = 4)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_1, newState.animations)
    }

    @Test
    fun teenAdult2Evolve(){
        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_1, discipline = MAX_DISCIPLINE, mistakes = 5)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_2, newState.animations)
    }

    @Test
    fun teenAdult3Evolve(){
        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_1, discipline = MAX_DISCIPLINE / 2, mistakes = 4)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_3, newState.animations)
    }

    @Test
    fun teenAdult4Evolve(){
        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_2, discipline = MAX_DISCIPLINE)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_4, newState.animations)
    }

    @Test
    fun teenAdult5Evolve(){
        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_1, discipline = 3, mistakes = 5)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_5, newState.animations)

        val teenState2 = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_2, discipline = 2)
        val (newState2, _) = teenAdultEvolve(teenState2)

        assertEquals(AgeStage.ADULT, newState2.ageStage)
        assertEquals(EvolutionAnimations.ADULT_5, newState2.animations)
    }

    @Test
    fun teenAdult6Evolve(){
        val teenState = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_1, discipline = MAX_DISCIPLINE/2, mistakes = 5)
        val (newState, _) = teenAdultEvolve(teenState)

        assertEquals(AgeStage.ADULT, newState.ageStage)
        assertEquals(EvolutionAnimations.ADULT_6, newState.animations)

        val teenState2 = PetState(ageStage = AgeStage.TEEN, animations = EvolutionAnimations.TEEN_2, discipline = 3)
        val (newState2, _) = teenAdultEvolve(teenState2)

        assertEquals(AgeStage.ADULT, newState2.ageStage)
        assertEquals(EvolutionAnimations.ADULT_6, newState2.animations)
    }
}