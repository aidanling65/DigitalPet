package com.example.tamagotchi.main.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.tamagotchi.intelligence.IntelligenceDifficulty
import com.example.tamagotchi.intelligence.IntelligenceGame
import com.example.tamagotchi.intelligence.PuzzleGames
import com.example.tamagotchi.main.data.data_logging.ActiveHistory
import com.example.tamagotchi.main.data.data_logging.EvolutionLog
import com.example.tamagotchi.main.data.data_logging.ExportData
import com.example.tamagotchi.main.data.data_logging.PassiveHistory
import com.example.tamagotchi.main.data.data_logging.SessionLog
import com.example.tamagotchi.main.data.data_logging.TamagotchiDatabase
import com.example.tamagotchi.main.data.data_logging.TamagotchiHistoryRepository
import com.example.tamagotchi.main.data.data_logging.storeEvolution
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.MAX_FITNESS
import com.example.tamagotchi.main.data.model.MAX_HUNGER
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.data.repository.UserSettingsImpl
import com.example.tamagotchi.main.domain.logic.GameLogicManager
import com.example.tamagotchi.main.domain.workers.evolution.death
import com.example.tamagotchi.main.domain.workers.utils.scheduleEvolutionWork
import com.example.tamagotchi.minigames.GameDifficulty
import com.example.tamagotchi.minigames.Minigames
import com.example.tamagotchi.step_tracker.repository.StepDatabase
import com.example.tamagotchi.step_tracker.repository.StepRepository
import com.example.tamagotchi.theme.AppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.LocalTime
import kotlin.random.Random

class GameViewModel(
    application: Application,
    val repository: TamagotchiRepository,
    val userSettingsImpl: UserSettingsImpl,
) : AndroidViewModel(application) {

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

    private val _showLossScreen = MutableStateFlow(false)
    val showLossScreen: StateFlow<Boolean> = _showLossScreen.asStateFlow()

    private val _showManual = MutableStateFlow(false)
    val showManual: StateFlow<Boolean> = _showManual.asStateFlow()

    private val _showStartup = MutableStateFlow(false)
    val showStartup: StateFlow<Boolean> = _showStartup.asStateFlow()

    private val _showEatingAnimation = MutableStateFlow(0)
    val showEatingAnimation: StateFlow<Int> = _showEatingAnimation.asStateFlow()

    private val _showStats = MutableStateFlow(false)
    val showStats: StateFlow<Boolean> = _showStats.asStateFlow()

    private val _appTheme = MutableStateFlow(AppTheme.PURPLE)
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()


    private var currentIntelligence: IntelligenceGame? = null

    private val gameLogicManager = GameLogicManager()
    private val stepDb = StepDatabase.getDatabase(getApplication())
    private val stepRepository = StepRepository(stepDb.stepsDao())

    private val historyDb = TamagotchiDatabase.getDatabase(getApplication())
    private val historyRepository = TamagotchiHistoryRepository(historyDb.historyDao())

    private var tempHistory: ActiveHistory? = null
    private var evolutionLog: EvolutionLog? = null
    private var passiveHistory: PassiveHistory? = null

    private var gameOpened: LocalDateTime = LocalDateTime.now()

    private val exportData: ExportData = ExportData(getApplication(), repository)
    private var isHistoryFetched = false

    init {
        viewModelScope.launch {
            userSettingsImpl.themeStream.collect { newTheme ->
                _appTheme.value = newTheme
            }
        }

        viewModelScope.launch {
            repository.tamagotchiStateFlow.collect { state ->
                _tamagotchiState.value = state.copy(loading = false)
                if(!isHistoryFetched) {
                    if (state.initial) {
                        _showStartup.value = true
                    }
                    fetchHistory()
                    isHistoryFetched = true
                }
            }
        }

        viewModelScope.launch {
            stepRepository.loadTodaysSteps().collect { steps ->
                Log.d("Steps", "Loaded steps: $steps")
                val stepGoal = tamagotchiState.value.stepGoal
                if (steps > stepGoal && !tamagotchiState.value.stepGoalHit) {
                    updateAndSave {
                        val updatedFitness = (it.fitness + 1).coerceAtMost(MAX_FITNESS)
                        it.copy(
                            steps = steps.toInt(),
                            fitness = updatedFitness,
                            stepGoalHit = true,
                            mistakes = if (updatedFitness == MAX_FITNESS) (it.mistakes - 1).coerceAtLeast(
                                0
                            ) else it.mistakes
                        )
                    }
                } else if (steps > stepGoal * 1.5 && !tamagotchiState.value.stepGoal2Hit) {
                    updateAndSave {
                        it.copy(
                            steps = steps.toInt(),
                            stepGoal2Hit = true,
                            mistakes = if (it.fitness == MAX_FITNESS) (it.mistakes - 1).coerceAtLeast(
                                0
                            ) else it.fitness
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
        if (showEatingAnimation.value > 0) {
            return
        }
        if (tamagotchiState.value.ageStage != AgeStage.DEAD && tamagotchiState.value.ageStage != AgeStage.EGG && !tamagotchiState.value.sleeping && !tamagotchiState.value.paused) {
            _showEatingAnimation.value += 1
        }
    }

    fun onEatingAnimationFinished() {
        if (tamagotchiState.value.hunger < MAX_HUNGER) {
            tempHistory?.timesFed++
        }

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

    fun gameScore(score: Int, game: Minigames) {
        updateAndSave { gameLogicManager.play(it, score) }
        when (game) {
            Minigames.JUMP -> tempHistory?.timesJumpPlayed++
            Minigames.FLAPPY -> tempHistory?.timesFlappyPlayed++
        }
    }

    fun onDismissGame() {
        _showGame.value = false
    }

    private fun launchNonogram() {
        currentIntelligence = IntelligenceGame.NONOGRAM
        _showNonogram.value = true
    }

    private fun launchSudoku() {
        currentIntelligence = IntelligenceGame.SUDOKU
        _showSudoku.value = true
    }

    fun onLaunchIntelligence() {
        when (currentIntelligence) {
            IntelligenceGame.SUDOKU -> launchSudoku()
            IntelligenceGame.NONOGRAM -> launchNonogram()
            else -> {
                val gameChoice = Random.nextInt(0, 2)
                when (gameChoice) {
                    0 -> launchSudoku()
                    1 -> launchNonogram()
                }
            }
        }
    }

    fun showWinScreen() {
        currentIntelligence = null
        _showWinScreen.value = true
    }

    fun showLossScreen(puzzle: PuzzleGames) {
        currentIntelligence = null
        _showLossScreen.value = true

        when (puzzle) {
            PuzzleGames.SUDOKU -> tempHistory?.sudokusFailed++
            PuzzleGames.NONOGRAM -> tempHistory?.nonogramsFailed++
        }
    }

    fun onDismissIntelligence() {
        Log.d("GameViewModel", "Dismissing intelligence")
        _showSudoku.value = false
        _showNonogram.value = false
        _showWinScreen.value = false
        _showLossScreen.value = false
    }

    fun learning(puzzle: PuzzleGames) {
        updateAndSave { gameLogicManager.learning(it) }
        when (puzzle) {
            PuzzleGames.SUDOKU -> tempHistory?.sudokusSolved++
            PuzzleGames.NONOGRAM -> tempHistory?.nonogramsSolved++
        }
    }

    fun clean() {
        updateAndSave { gameLogicManager.clean(it) }
        tempHistory?.timesCleaned++
    }

    fun heal() {
        updateAndSave { gameLogicManager.heal(it) }
        tempHistory?.timesHealed++
    }

    fun light() {
        viewModelScope.launch {
            if (tamagotchiState.value.light) {
                updateAndSave { it.copy(lightAnimationState = 1) }
                delay(250)
                updateAndSave { it.copy(lightAnimationState = 0, light = false) }
                tempHistory?.timesLightsOut++

            } else {
                updateAndSave { it.copy(lightAnimationState = 2, light = true) }
                delay(400)
                updateAndSave { it.copy(lightAnimationState = 0) }
            }
        }

    }

    fun discipline() {
        updateAndSave { gameLogicManager.discipline(it) }
        tempHistory?.timesDisciplined++
    }

    fun onStartupOpen() {
        _showStartup.value = true
        _showResetDialog.value = false
        tempHistory?.settingsUsed++
    }

    fun onDismissStartup() {
        Log.d("StartupDialog", "here")
        _showStartup.value = false
    }

    fun onManualClicked() {
        _showManual.value = true
        tempHistory?.manualUsed++
    }

    fun onDismissManual() {
        _showManual.value = false
    }

    private fun checkEvolve() {
        Log.d("GameViewModel", "Checking evolution")
        Log.d("GameViewModel", "${evolutionLog?.ageStage}  ${tamagotchiState.value.ageStage}")
        if (evolutionLog != null) {
            if (evolutionLog?.ageStage != tamagotchiState.value.ageStage) {
                if (evolutionLog?.ageStage == AgeStage.DEAD) {
                    updateAndSave {
                        death(getApplication(), it)
                    }
                } else {
                    val evolveFunction = tamagotchiState.value.ageStage.evolve
                    updateAndSave {
                        val (evolvedState, _) = evolveFunction!!(getApplication(), it, false)
                        evolvedState
                    }
                }
            }
        }
    }

    fun onDismissEvolution() {
        updateAndSave { it -> it.copy(hasEvolved = false) }
    }

    fun pauseGame() {
        updateAndSave { it.copy(paused = !it.paused) }
        if (tamagotchiState.value.paused) {
            tempHistory?.pausesUsed++
        }
    }

    fun onStatsClicked() {
        _showStats.value = true
    }

    fun onDismissStats() {
        _showStats.value = false
    }

    fun getStats(): Pair<ActiveHistory, PassiveHistory>{
        return Pair(tempHistory ?: ActiveHistory(), passiveHistory?: PassiveHistory())
    }

    fun onResetClicked() {
        _showResetDialog.value = true
    }

    fun onDismissResetDialog() {
        _showResetDialog.value = false
    }

    fun confirmReset() {
        _showStartup.value = true
        tempHistory?.resets++
        updateAndSave {
            TamagotchiState()
        }
        viewModelScope.launch {
            storeEvolution(
                getApplication(),
                EvolutionLog(
                    ageStage = AgeStage.EGG,
                    evolutionType = EvolutionAnimations.EGG
                )
            )
        }
        WorkManager.getInstance(getApplication()).cancelAllWork()
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

    fun updatePuzzleDifficulty(difficulty: IntelligenceDifficulty) {
        updateAndSave { it.copy(puzzleDifficulty = difficulty) }
    }

    fun updateGameDifficulty(difficulty: GameDifficulty) {
        updateAndSave { it.copy(gameDifficulty = difficulty) }
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
            scheduleEvolutionWork(getApplication(), tamagotchiState.value)
        }
    }

    suspend fun fetchHistory() {
        gameOpened = LocalDateTime.now()
        val previousActives = historyRepository.getLatestActive()
        val previousPassive = historyRepository.getLatestPassive()

        tempHistory = previousActives?.copy(
            id = 0,
        ) ?: ActiveHistory(id = 0)

        passiveHistory = previousPassive?: PassiveHistory(id = 0)

        evolutionLog = historyRepository.getLatestEvolution()
        checkEvolve()
    }

    fun uploadHistory() {
        viewModelScope.launch {
            val sessionId = historyRepository.storeSession(
                SessionLog(
                    gameOpened = gameOpened.toString(),
                    gameClosed = LocalDateTime.now().toString()
                )
            )
            tempHistory?.let {
                it.sessionId = sessionId.toInt()
                historyRepository.storeHistory(it)
                Log.d("GameViewModel", "History saved on exit")
            }
        }
    }

    fun exportData() {
        viewModelScope.launch {
            exportData.exportDataForSharing()
        }
    }

    fun updateTheme(appTheme: AppTheme) {
        userSettingsImpl.theme = appTheme
    }
}