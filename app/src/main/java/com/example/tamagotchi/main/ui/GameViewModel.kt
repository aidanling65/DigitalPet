package com.example.tamagotchi.main.ui

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.logic.GameLogicManager
import com.example.tamagotchi.main.domain.workers.utils.scheduleEvolutionWork
import com.example.tamagotchi.step_tracker.repository.StepDatabase
import com.example.tamagotchi.step_tracker.repository.StepRepository
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalTime
import kotlin.random.Random

class GameViewModel(
    private val context: Context,
    val sudokuViewModel: SudokuViewModel,
    val repository: TamagotchiRepository
) :
    ViewModel() {
    private val _tamagotchiState = MutableStateFlow(TamagotchiState())
    val tamagotchiState: StateFlow<TamagotchiState> = _tamagotchiState.asStateFlow()

    private val _showResetDialog = MutableStateFlow(false)
    val showResetDialog: StateFlow<Boolean> = _showResetDialog.asStateFlow()

    private val _showGame = MutableStateFlow(false)
    val showGame: StateFlow<Boolean> = _showGame.asStateFlow()

    private val _showSudoku = MutableStateFlow(false)
    val showSudoku: StateFlow<Boolean> = _showSudoku.asStateFlow()

    private val _showNonogram = MutableStateFlow(false)
    val showNonogram: StateFlow<Boolean> = _showNonogram.asStateFlow()

    private val _showWinScreen = MutableStateFlow(false)
    val showWinScreen: StateFlow<Boolean> = _showWinScreen.asStateFlow()

    private val _showManual = MutableStateFlow(false)
    val showManual: StateFlow<Boolean> = _showManual.asStateFlow()

    private val _showStartup = MutableStateFlow(false)
    val showStartup: StateFlow<Boolean> = _showStartup.asStateFlow()

    private val _showEatingAnimation = MutableStateFlow(0)
    val showEatingAnimation: StateFlow<Int> = _showEatingAnimation.asStateFlow()


    private val gameLogicManager = GameLogicManager()
    private val stepDb = StepDatabase.getDatabase(context)
    private val stepRepository = StepRepository(stepDb.stepsDao())

    init {
        viewModelScope.launch {
            val initialState = repository.getState()
            if (initialState.initial) {
                _showStartup.value = true
            }
            repository.tamagotchiStateFlow.collect { state ->
                _tamagotchiState.value = state.copy(loading=false)
            }
        }

        viewModelScope.launch {
            stepRepository.loadTodaysSteps().collect { steps ->
                Log.d("Steps", "Loaded steps: $steps")
                val stepGoal = tamagotchiState.value.stepGoal
                if (tamagotchiState.value.steps < stepGoal && steps > stepGoal) {
                    updateAndSave {
                        val updatedFitness = (it.fitness + 1).coerceAtMost(MAX_FITNESS)
                        it.copy(
                            steps = steps.toInt(),
                            fitness = updatedFitness,
                            physicalMistakes = if (updatedFitness == MAX_FITNESS) (it.physicalMistakes - 1).coerceAtLeast(
                                0
                            ) else it.physicalMistakes
                        )
                    }
                } else {
                    updateAndSave { it.copy(steps = steps.toInt()) }
                }
            }
        }
    }

    private fun updateAndSave(transform: (currentState: TamagotchiState) -> TamagotchiState) {
        viewModelScope.launch {
            repository.updateState(transform)
        }
    }

    fun feed() {
        if(showEatingAnimation.value > 0){
            return
        }
        if (tamagotchiState.value.ageStage != AgeStage.DEAD && tamagotchiState.value.ageStage != AgeStage.EGG && !tamagotchiState.value.sleeping && !tamagotchiState.value.paused) {
            _showEatingAnimation.value += 1
        }
    }

    fun onEatingAnimationFinished() {
        updateAndSave { gameLogicManager.feed(it) }
        _showEatingAnimation.value = 0
    }

    fun play() {
        if (!tamagotchiState.value.sleeping &&
            tamagotchiState.value.ageStage != AgeStage.DEAD &&
            tamagotchiState.value.ageStage != AgeStage.EGG
        ) {
            _showGame.value = true
        }
    }

    fun gameScore(score: Int) {
        updateAndSave { gameLogicManager.play(it, score) }
    }

    fun onDismissGame() {
        _showGame.value = false
    }

    private fun launchNonogram(){
        _showNonogram.value = true
    }

    private fun launchSudoku() {
        sudokuViewModel.sudokuGame.fetchNewSudoku()
        _showSudoku.value = true
    }

    fun onLaunchIntelligence(){
        val gameChoice = Random.nextInt(0, 2)
        when(gameChoice){
            0 -> launchSudoku()
            1 -> launchNonogram()
        }
    }

    fun showWinScreen(){
        _showWinScreen.value = true
    }

    fun onDismissIntelligence() {
        Log.d("GameViewModel", "Dismissing intelligence")
        _showSudoku.value = false
        _showNonogram.value = false
        _showWinScreen.value = false
    }

    fun learning() {
        updateAndSave { gameLogicManager.learning(it) }
    }

    fun clean() {
        updateAndSave { gameLogicManager.clean(it) }
    }

    fun heal() {
        updateAndSave { gameLogicManager.heal(it) }
    }

    fun light() {
        viewModelScope.launch {
            if (tamagotchiState.value.light) {
                updateAndSave { it.copy(lightAnimationState = 1) }
                delay(500)
                updateAndSave { it.copy(light = false) }
                delay(500)
                updateAndSave { it.copy(lightAnimationState = 0) }

            } else {
                updateAndSave { it.copy(lightAnimationState = 2, light = false) }
                delay(100)
                updateAndSave { it.copy(lightAnimationState = 3, light = true) }
            }
        }
    }

    fun discipline() {
        updateAndSave { gameLogicManager.discipline(it) }
    }

    fun onStartupOpen() {
        _showStartup.value = true
    }

    fun onDismissStartup() {
        Log.d("StartupDialog", "here")
        _showStartup.value = false
    }

    fun onManualClicked() {
        _showManual.value = true
    }

    fun onDismissManual() {
        _showManual.value = false
    }

    fun onDismissEvolution() {
        updateAndSave { it -> it.copy(hasEvolved = false) }
    }

    fun pauseGame() {
        updateAndSave { it.copy(paused = !it.paused) }
    }

    fun onResetClicked() {
        _showResetDialog.value = true
    }

    fun onDismissResetDialog() {
        _showResetDialog.value = false
    }


    fun confirmReset() {
        _showStartup.value = true
        updateAndSave {
            TamagotchiState()
        }
        onDismissResetDialog()
        WorkManager.getInstance(context).cancelAllWork()
    }

    fun submitStepsGoal(stepGoal: Int) {
        updateAndSave {
            it.copy(
                stepGoal = stepGoal
            )
        }
    }

    fun updateBedTime(bedTime: LocalTime) {
        updateAndSave { it.copy(bedTime = bedTime) }
    }

    fun updateWakeTime(wakeTime: LocalTime) {
        updateAndSave { it.copy(wakeTime = wakeTime) }
    }

    fun setupNewGame() {
        _showStartup.value = false
        _showResetDialog.value = false

        viewModelScope.launch {
            updateAndSave {
                it.copy(
                    initial = false
                )
            }
            scheduleEvolutionWork(context, tamagotchiState.value)
        }
    }
}