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
import com.example.tamagotchi.sudoku.ui.SudokuViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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

    private val _showManual = MutableStateFlow(false)
    val showManual: StateFlow<Boolean> = _showManual.asStateFlow()

    private val gameLogicManager = GameLogicManager()
    private val stepDb = StepDatabase.getDatabase(context)
    private val stepRepository = StepRepository(stepDb.stepsDao())

    init {
        viewModelScope.launch {
            val initialState = repository.getState()
            if (initialState.initial) {
                setupNewGame()
            }
            repository.tamagotchiStateFlow.collect { state ->
                _tamagotchiState.value = state.copy()
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
        updateAndSave { gameLogicManager.feed(it) }
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

    fun launchSudoku() {
        sudokuViewModel.sudokuGame.fetchNewSudoku()
        _showSudoku.value = true
    }

    fun onDismissSudoku() {
        _showSudoku.value = false
    }

    fun learning() {
        _showSudoku.value = false
        updateAndSave { gameLogicManager.learning(it) }
    }

    fun clean() {
        updateAndSave { gameLogicManager.clean(it) }
    }

    fun heal() {
        updateAndSave { gameLogicManager.heal(it) }
    }

    fun light() {
        updateAndSave { gameLogicManager.light(it) }
    }

    fun discipline() {
        updateAndSave { gameLogicManager.discipline(it) }
    }

    fun onManualClicked(){
        _showManual.value = true
    }

    fun onDismissManual(){
        _showManual.value = false
    }

    fun onDismissEvolution() {
        updateAndSave { it -> it.copy(hasEvolved = false) }
    }

    fun onResetClicked() {
        _showResetDialog.value = true
    }

    fun onDismissResetDialog() {
        _showResetDialog.value = false
    }

    fun confirmReset() {
        setupNewGame()
        onDismissResetDialog()
    }

    fun setupNewGame() {
        WorkManager.getInstance(context).cancelAllWork()
        viewModelScope.launch {
            val resetState = TamagotchiState(initial = false)
            repository.saveState(resetState)
            scheduleEvolutionWork(context, resetState)
        }
    }
}