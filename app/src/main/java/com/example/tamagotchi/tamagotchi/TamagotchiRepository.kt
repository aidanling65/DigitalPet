package com.example.tamagotchi.tamagotchi

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "tamagotchi_prefs")

class TamagotchiRepository(private val context: Context) {

    private val AGE = intPreferencesKey("age")
    private val WEIGHT = intPreferencesKey("weight")
    private val HUNGER = intPreferencesKey("hunger")
    private val HAPPINESS = intPreferencesKey("happiness")
    private val DISCIPLINE = intPreferencesKey("discipline")
    private val LIGHT = booleanPreferencesKey("light")
    private val MEDICINE_TAKEN = booleanPreferencesKey("medicineTaken")
    private val SICK = booleanPreferencesKey("sick")
    private val POOP = booleanPreferencesKey("poop")
    private val MISBEHAVING = booleanPreferencesKey("misbehaving")
    private val SLEEPING = booleanPreferencesKey("sleeping")
    private val AGE_STAGE = stringPreferencesKey("age stage")
    private val ANIMATIONS = stringPreferencesKey("animations")
    
    val tamagotchiStateFlow: Flow<TamagotchiState> = context.dataStore.data
        .map { prefs ->
            val defaultState = TamagotchiState()
            TamagotchiState(
                age = prefs[AGE] ?: defaultState.age,
                weight = prefs[WEIGHT] ?: defaultState.weight,
                hunger = prefs[HUNGER] ?: defaultState.hunger,
                happiness = prefs[HAPPINESS] ?: defaultState.happiness,
                discipline = prefs[DISCIPLINE] ?: defaultState.discipline,
                light = prefs[LIGHT] ?: defaultState.light,
                medicineTaken = prefs[MEDICINE_TAKEN] ?: defaultState.medicineTaken,
                sick = prefs[SICK] ?: defaultState.sick,
                poop = prefs[POOP] ?: defaultState.poop,
                misbehaving = prefs[MISBEHAVING] ?: defaultState.misbehaving,
                sleeping = prefs[SLEEPING] ?: defaultState.sleeping,
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
            updated[HAPPINESS] = current.happiness
            updated[DISCIPLINE] = current.discipline
            updated[LIGHT] = current.light
            updated[MEDICINE_TAKEN] = current.medicineTaken
            updated[SICK] = current.sick
            updated[POOP] = current.poop
            updated[MISBEHAVING] = current.misbehaving
            updated[SLEEPING] = current.sleeping
            updated[AGE_STAGE] = current.ageStage.name
            updated[ANIMATIONS] = current.animations.name
        }
    }

    suspend fun getState(): TamagotchiState {
        return tamagotchiStateFlow.first()
    }
}