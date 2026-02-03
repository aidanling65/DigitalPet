package com.example.tamagotchi.main.data.repository

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.MutablePreferences
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val Context.dataStore by preferencesDataStore(name = "tamagotchi_prefs")

class TamagotchiRepository(private val context: Context) {

    private val INITIAL = booleanPreferencesKey("initial")
    private val AGE = intPreferencesKey("age")
    private val WEIGHT = intPreferencesKey("weight")
    private val HUNGER = intPreferencesKey("hunger")
    private val HAPPINESS = intPreferencesKey("happiness")
    private val DISCIPLINE = intPreferencesKey("discipline")
    private val INTELLIGENCE = intPreferencesKey("intelligence")

    private val STEPS = intPreferencesKey("steps")
    private val RESET_STEPS = booleanPreferencesKey("reset_steps")
    private val DAILY_STEP_BASELINE = intPreferencesKey("daily_step_baseline")
    private val FITNESS = intPreferencesKey("fitness")
    private val STEP_GOAL = intPreferencesKey("step_goal")


    private val LIGHT = booleanPreferencesKey("light")
    private val LIGHT_STATE = intPreferencesKey("light_state")
    private val SLEEPING = booleanPreferencesKey("sleeping")

    private val MEDICINE_TAKEN = booleanPreferencesKey("medicineTaken")
    private val SICK = booleanPreferencesKey("sick")
    private val POOP = booleanPreferencesKey("poop")
    private val MISBEHAVING = booleanPreferencesKey("misbehaving")
    private val AGE_STAGE = stringPreferencesKey("age stage")
    private val ANIMATIONS = stringPreferencesKey("animations")
    private val MENTAL_MISTAKES = intPreferencesKey("mental_mistakes")
    private val PHYSICAL_MISTAKES = intPreferencesKey("physical_mistakes")
    private val MISTAKES = intPreferencesKey("mistakes")
    private val HAS_EVOLVED = booleanPreferencesKey("has_evolved")
    private val LAST_EVOLVE = stringPreferencesKey("last_evolve")
    private val PAUSED = booleanPreferencesKey("paused")

    private val BED_TIME = stringPreferencesKey("bed_time")
    private val WAKE_TIME = stringPreferencesKey("wake_time")

    private fun stateFromPrefs(prefs: Preferences): TamagotchiState {
        val defaultState = TamagotchiState()
        return TamagotchiState(
            initial = prefs[INITIAL] ?: defaultState.initial,
            age = prefs[AGE] ?: defaultState.age,
            weight = prefs[WEIGHT] ?: defaultState.weight,
            hunger = prefs[HUNGER] ?: defaultState.hunger,
            happiness = prefs[HAPPINESS] ?: defaultState.happiness,
            steps = prefs[STEPS] ?: defaultState.steps,
            resetSteps = prefs[RESET_STEPS] ?: defaultState.resetSteps,
            dailyStepBaseline = prefs[DAILY_STEP_BASELINE] ?: defaultState.dailyStepBaseline,
            fitness = prefs[FITNESS] ?: defaultState.fitness,
            stepGoal = prefs[STEP_GOAL] ?: defaultState.stepGoal,
            discipline = prefs[DISCIPLINE] ?: defaultState.discipline,
            intelligence = prefs[INTELLIGENCE] ?: defaultState.intelligence,
            light = prefs[LIGHT] ?: defaultState.light,
            medicineTaken = prefs[MEDICINE_TAKEN] ?: defaultState.medicineTaken,
            sick = prefs[SICK] ?: defaultState.sick,
            lightAnimationState = prefs[LIGHT_STATE] ?: defaultState.lightAnimationState,
            poop = prefs[POOP] ?: defaultState.poop,
            misbehaving = prefs[MISBEHAVING] ?: defaultState.misbehaving,
            sleeping = prefs[SLEEPING] ?: defaultState.sleeping,
            mentalMistakes = prefs[MENTAL_MISTAKES] ?: defaultState.mentalMistakes,
            physicalMistakes = prefs[PHYSICAL_MISTAKES] ?: defaultState.physicalMistakes,
            mistakes = prefs[MISTAKES] ?: defaultState.mistakes,
            ageStage = AgeStage.valueOf(prefs[AGE_STAGE] ?: defaultState.ageStage.name),
            animations = EvolutionAnimations.valueOf(
                prefs[ANIMATIONS] ?: defaultState.animations.name
            ),
            lastEvolve = LocalDateTime.parse(
                prefs[LAST_EVOLVE]
                    ?: defaultState.lastEvolve.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                DateTimeFormatter.ISO_LOCAL_DATE_TIME
            ),
            hasEvolved = prefs[HAS_EVOLVED] ?: defaultState.hasEvolved,
            paused = prefs[PAUSED] ?: defaultState.paused,
            bedTime = LocalTime.parse(prefs[BED_TIME] ?: defaultState.bedTime.toString()),
            wakeTime = LocalTime.parse(prefs[WAKE_TIME] ?: defaultState.wakeTime.toString())
        )
    }

    val tamagotchiStateFlow: Flow<TamagotchiState> = context.dataStore.data
        .map { prefs ->
            stateFromPrefs(prefs)
        }

    suspend fun updateState(transform: (currentState: TamagotchiState) -> TamagotchiState): TamagotchiState {
        Log.d("Repository", "Updating state")
        val updatedPrefs = context.dataStore.updateData {prefs->
            val current = stateFromPrefs(prefs)

            val transformed = transform(current)
            val updatedPrefs = prefs.toMutablePreferences()
            saveStateInternal(transformed, updatedPrefs)

            updatedPrefs
        }

        return stateFromPrefs(updatedPrefs)
    }

    private fun saveStateInternal(current: TamagotchiState, updated: MutablePreferences) {
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
        updated[FITNESS] = current.fitness
        updated[STEP_GOAL] = current.stepGoal
        updated[DISCIPLINE] = current.discipline
        updated[INTELLIGENCE] = current.intelligence
        updated[LIGHT] = current.light
        updated[LIGHT_STATE] = current.lightAnimationState
        updated[MEDICINE_TAKEN] = current.medicineTaken
        updated[SICK] = current.sick
        updated[POOP] = current.poop
        updated[MISBEHAVING] = current.misbehaving
        updated[SLEEPING] = current.sleeping
        updated[PHYSICAL_MISTAKES] = current.physicalMistakes
        updated[MENTAL_MISTAKES] = current.mentalMistakes
        updated[MISTAKES] = current.mistakes
        updated[AGE_STAGE] = current.ageStage.name
        updated[ANIMATIONS] = current.animations.name
        updated[LAST_EVOLVE] = current.lastEvolve.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        updated[HAS_EVOLVED] = current.hasEvolved
        updated[PAUSED] = current.paused
        updated[BED_TIME] = current.bedTime.toString()
        updated[WAKE_TIME] = current.wakeTime.toString()
    }
}