package com.example.tamagotchi.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tamagotchi.data.model.AgeStage
import com.example.tamagotchi.data.model.EvolutionAnimations
import com.example.tamagotchi.data.model.TamagotchiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "tamagotchi_prefs")

class TamagotchiRepository(private val context: Context) {

    private val AGE = intPreferencesKey("age")
    private val WEIGHT = intPreferencesKey("weight")
    private val HUNGER = intPreferencesKey("hunger")
    private val HUNGER_DECAY = intPreferencesKey("hunger_decay")
    private val HAPPINESS = intPreferencesKey("happiness")
    private val HAPPINESS_DECAY = intPreferencesKey("happiness_decay")
    private val DISCIPLINE = intPreferencesKey("discipline")
    private val LIGHT = booleanPreferencesKey("light")
    private val MEDICINE_TAKEN = booleanPreferencesKey("medicineTaken")
    private val SICK = booleanPreferencesKey("sick")
    private val POOP = booleanPreferencesKey("poop")
    private val MISBEHAVING = booleanPreferencesKey("misbehaving")
    private val SLEEPING = booleanPreferencesKey("sleeping")
    private val AGE_STAGE = stringPreferencesKey("age stage")
    private val ANIMATIONS = stringPreferencesKey("animations")
    private val MENTAL_MISTAKES = intPreferencesKey("mental_mistakes")
    private val PHYSICAL_MISTAKES = intPreferencesKey("physical_mistakes")
    
    val tamagotchiStateFlow: Flow<TamagotchiState> = context.dataStore.data
        .map { prefs ->
            val defaultState = TamagotchiState()
            TamagotchiState(
                age = prefs[AGE] ?: defaultState.age,
                weight = prefs[WEIGHT] ?: defaultState.weight,
                hunger = prefs[HUNGER] ?: defaultState.hunger,
                hungerDecayCounter = prefs[HUNGER_DECAY] ?: defaultState.hungerDecayCounter,
                happiness = prefs[HAPPINESS] ?: defaultState.happiness,
                happinessDecayCounter = prefs[HAPPINESS_DECAY] ?: defaultState.happinessDecayCounter,
                discipline = prefs[DISCIPLINE] ?: defaultState.discipline,
                light = prefs[LIGHT] ?: defaultState.light,
                medicineTaken = prefs[MEDICINE_TAKEN] ?: defaultState.medicineTaken,
                sick = prefs[SICK] ?: defaultState.sick,
                poop = prefs[POOP] ?: defaultState.poop,
                misbehaving = prefs[MISBEHAVING] ?: defaultState.misbehaving,
                sleeping = prefs[SLEEPING] ?: defaultState.sleeping,
                mentalMistakes = prefs[MENTAL_MISTAKES] ?: defaultState.mentalMistakes,
                physicalMistakes = prefs[PHYSICAL_MISTAKES] ?: defaultState.physicalMistakes,
                ageStage = AgeStage.valueOf(prefs[AGE_STAGE] ?: defaultState.ageStage.name),
                animations = EvolutionAnimations.valueOf(
                    prefs[ANIMATIONS] ?: defaultState.animations.name
                )
            )

        }

    suspend fun saveState(current: TamagotchiState) {
        context.dataStore.edit { updated ->
            updated[AGE] = current.age
            updated[WEIGHT] = current.weight
            updated[HUNGER] = current.hunger
            updated[HUNGER_DECAY] = current.hungerDecayCounter
            updated[HAPPINESS] = current.happiness
            updated[HAPPINESS_DECAY] = current.happinessDecayCounter
            updated[DISCIPLINE] = current.discipline
            updated[LIGHT] = current.light
            updated[MEDICINE_TAKEN] = current.medicineTaken
            updated[SICK] = current.sick
            updated[POOP] = current.poop
            updated[MISBEHAVING] = current.misbehaving
            updated[SLEEPING] = current.sleeping
            updated[PHYSICAL_MISTAKES] = current.physicalMistakes
            updated[MENTAL_MISTAKES] = current.mentalMistakes
            updated[AGE_STAGE] = current.ageStage.name
            updated[ANIMATIONS] = current.animations.name
        }
    }

    suspend fun getState(): TamagotchiState {
        return tamagotchiStateFlow.first()
    }
}