package com.example.digitalpet.main.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.digitalpet.intelligence.PuzzleGames
import com.example.digitalpet.intelligence.nonogram.NonogramViewModel
import com.example.digitalpet.intelligence.nonogram.NonogramViewModelFactory
import com.example.digitalpet.intelligence.nonogram.ui.NonogramDialog
import com.example.digitalpet.intelligence.sudoku.ui.SudokuViewModel
import com.example.digitalpet.intelligence.sudoku.ui.SudokuViewModelFactory
import com.example.digitalpet.intelligence.sudoku.ui.components.SudokuDialog
import com.example.digitalpet.main.ui.GameViewModel
import com.example.digitalpet.main.ui.components.settings.SettingsDialog
import com.example.digitalpet.main.ui.components.manual.Manual
import com.example.digitalpet.minigames.ui.GameDialog

@Composable
fun Dialogs(gameViewModel: GameViewModel) {

    val petState by gameViewModel.petState.collectAsState()
    val showStartup by gameViewModel.showStartup.collectAsState()
    val showReset by gameViewModel.showResetDialog.collectAsState()
    val showGame by gameViewModel.showGame.collectAsState()
    val showSudoku by gameViewModel.showSudoku.collectAsState()
    val showNonogram by gameViewModel.showNonogram.collectAsState()
    val showWinScreen by gameViewModel.showWinScreen.collectAsState()
    val showLoss by gameViewModel.showLossScreen.collectAsState()
    val showManual by gameViewModel.showManual.collectAsState()
    val showStats by gameViewModel.showStats.collectAsState()
    val (activeStats, passiveStats) = gameViewModel.getStats()
    val appTheme by gameViewModel.appTheme.collectAsState()
    val startupDismiss = {
        if (petState.initial) {
            gameViewModel.setupNewGame()
        } else {
            gameViewModel.onDismissStartup()
        }
    }
    Box(
        Modifier
            .fillMaxSize()
            .background(Color.Transparent)
    ) {
        DialogBase(
            showStartup, onDismissRequest = { startupDismiss() },
            properties = DialogProperties(
                dismissOnBackPress = !petState.initial,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) { onDismissRequest ->
            SettingsDialog(
                petState,
                { gameViewModel.submitStepsGoal(it) },
                { gameViewModel.updateBedTime(it) },
                { gameViewModel.updateWakeTime(it) },
                { gameViewModel.updatePuzzleDifficulty(it) },
                { gameViewModel.updateGameDifficulty(it) },
                { gameViewModel.exportData() },
                appTheme,
                {gameViewModel.updateTheme(it)},
                petState.color,
                {gameViewModel.updateColor(it)},
                onDismissRequest = onDismissRequest,
            )
        }

        DialogBase(
            petState.hasEvolved,
            onDismissRequest = { gameViewModel.onDismissEvolution() }) { onDismissRequest ->
            EvolutionDialog(
                petState,
                onDismissRequest = onDismissRequest
            )
        }
        DialogBase(
            showReset,
            onDismissRequest = { gameViewModel.onDismissResetDialog() }) { onDismissRequest ->
            ResetDialog(
                onDismissRequest = onDismissRequest,
                onConfirmation = { gameViewModel.confirmReset() }
            )
        }
        DialogBase(
            showGame, onDismissRequest = { gameViewModel.onDismissGame() },
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) { onDismissRequest ->
            GameDialog(
                petState,
            ) { score, game -> gameViewModel.gameScore(score, game) }
        }

        val puzzleModifier =
            if (showWinScreen) Modifier.shadow(5.dp, shape = RoundedCornerShape(10)) else Modifier
        DialogBase(
            showSudoku,
            modifier = puzzleModifier,
            onDismissRequest = { gameViewModel.onDismissIntelligence() }) { onDismissRequest ->

            val missingDigits = petState.puzzleDifficulty.sudokuDigits
            val sudokuViewModel: SudokuViewModel = viewModel(
                factory = SudokuViewModelFactory(missingDigits)
            )

            SudokuDialog(
                petState = petState,
                sudokuViewModel,
                showWinScreen,
                showLoss,
                {
                    gameViewModel.learning(PuzzleGames.SUDOKU)
                    gameViewModel.showWinScreen()
                    sudokuViewModel.sudokuGame.fetchNewSudoku(missingDigits)
                },
                {
                    sudokuViewModel.sudokuGame.fetchNewSudoku(missingDigits)
                    gameViewModel.showLossScreen(PuzzleGames.SUDOKU)
                },
                onDismissRequest
            )
        }

        DialogBase(
            showNonogram,
            modifier = puzzleModifier,
            onDismissRequest = { gameViewModel.onDismissIntelligence() })
        { onDismissRequest ->
            val nonogramViewModel: NonogramViewModel = viewModel(
                factory = NonogramViewModelFactory(
                    10,
                    10,
                    petState.puzzleDifficulty.nonogramOdds
                )
            )
            NonogramDialog(
                petState,
                nonogramViewModel,
                showWinScreen,
                {
                    nonogramViewModel.fetchNewNonogram()
                    gameViewModel.learning(PuzzleGames.NONOGRAM)
                    gameViewModel.showWinScreen()
                },
                showLoss,
                {
                    nonogramViewModel.fetchNewNonogram()
                    gameViewModel.showLossScreen(PuzzleGames.NONOGRAM)
                },
                onDismissRequest
            )
        }

        DialogBase(
            showStats,
            onDismissRequest = { gameViewModel.onDismissStats() },
        ) {
            StatsDialog(petState, activeStats, passiveStats)
        }

        Manual(showManual, petState) { gameViewModel.onDismissManual() }
    }
}