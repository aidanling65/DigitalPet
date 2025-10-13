package com.example.tamagotchi

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TamagotchiRepository(private val context: Context) {

    val Context.dataStore by preferencesDataStore(name = "tamagotchi_prefs")

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

    val tamagotchiStateFlow: Flow<TamagotchiState> = context.dataStore.data
        .map { prefs ->
            TamagotchiState(
                age = prefs[AGE] ?: 0,
                weight = prefs[WEIGHT] ?: 5,
                hunger = prefs[HUNGER] ?: 0,
                happiness = prefs[HAPPINESS] ?: 0,
                discipline = prefs[DISCIPLINE] ?: 0,
                light = prefs[LIGHT] ?: true,
                medicineTaken = prefs[MEDICINE_TAKEN] ?: false,
                sick = prefs[SICK] ?: false,
                poop = prefs[POOP] ?: false,
                misbehaving = prefs[MISBEHAVING] ?: false
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
        }
    }

    suspend fun getState(): TamagotchiState {
        return context.dataStore.data
            .map { prefs ->
                TamagotchiState(
                    age = prefs[AGE] ?: 0,
                    weight = prefs[WEIGHT] ?: 5,
                    hunger = prefs[HUNGER] ?: 0,
                    happiness = prefs[HAPPINESS] ?: 0,
                    discipline = prefs[DISCIPLINE] ?: 0,
                    light = prefs[LIGHT] ?: true,
                    medicineTaken = prefs[MEDICINE_TAKEN] ?: false,
                    sick = prefs[SICK] ?: false,
                    poop = prefs[POOP] ?: false,
                    misbehaving = prefs[MISBEHAVING] ?: false
                )
            }
            .first()
    }
}