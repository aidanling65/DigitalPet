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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

val Context.dataStore by preferencesDataStore(name = "tamagotchi_prefs")

class TamagotchiRepository(private val context: Context) {

    private val INITIAL = booleanPreferencesKey("initial")
    private val AGE = intPreferencesKey("age")
    private val WEIGHT = intPreferencesKey("weight")
    private val HUNGER = intPreferencesKey("hunger")
    private val HAPPINESS = intPreferencesKey("happiness")
    private val STEPS = intPreferencesKey("steps")
    private val RESET_STEPS = booleanPreferencesKey("reset_steps")
    private val DAILY_STEP_BASELINE = intPreferencesKey("daily_step_baseline")
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

    private val saveMutex = Mutex()

    val tamagotchiStateFlow: Flow<TamagotchiState> = context.dataStore.data
        .map { prefs ->
            val defaultState = TamagotchiState()
            TamagotchiState(
                initial = prefs[INITIAL] ?: defaultState.initial,
                age = prefs[AGE] ?: defaultState.age,
                weight = prefs[WEIGHT] ?: defaultState.weight,
                hunger = prefs[HUNGER] ?: defaultState.hunger,
                happiness = prefs[HAPPINESS] ?: defaultState.happiness,
                steps = prefs[STEPS] ?: defaultState.steps,
                resetSteps = prefs[RESET_STEPS] ?: defaultState.resetSteps,
                dailyStepBaseline = prefs[DAILY_STEP_BASELINE] ?: defaultState.dailyStepBaseline,
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

    suspend fun updateState(transform: (currentState: TamagotchiState) -> TamagotchiState) {
        saveMutex.withLock {
            val currentState = tamagotchiStateFlow.first()
            val newState = transform(currentState)
            saveStateInternal(newState)
        }
    }

    suspend fun saveState(currentState: TamagotchiState) {
        saveMutex.withLock {
            saveStateInternal(currentState)
        }
    }

    private suspend fun saveStateInternal(current: TamagotchiState) {
        context.dataStore.edit { updated ->
            updated[INITIAL] = current.initial
            updated[AGE] = current.age
            updated[WEIGHT] = current.weight
            updated[HUNGER] = current.hunger
            updated[HAPPINESS] = current.happiness
            updated[STEPS] = current.steps
            updated[RESET_STEPS] = current.resetSteps
            val baseline = current.dailyStepBaseline
            if (baseline != null) {
                updated[DAILY_STEP_BASELINE] = baseline
            } else {
                updated.remove(DAILY_STEP_BASELINE)
            }
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