package com.example.tamagotchi.main.ui.components.dialogs

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
import com.example.tamagotchi.intelligence.PuzzleGames
import com.example.tamagotchi.intelligence.nonogram.NonogramViewModel
import com.example.tamagotchi.intelligence.nonogram.NonogramViewModelFactory
import com.example.tamagotchi.intelligence.nonogram.ui.NonogramDialog
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModel
import com.example.tamagotchi.intelligence.sudoku.ui.SudokuViewModelFactory
import com.example.tamagotchi.intelligence.sudoku.ui.components.SudokuDialog
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.dialogs.startup.StartupDialog
import com.example.tamagotchi.main.ui.components.manual.Manual
import com.example.tamagotchi.minigames.ui.GameDialog

@Composable
fun Dialogs(gameViewModel: GameViewModel) {

    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    val showStartup by gameViewModel.showStartup.collectAsState()
    val showReset by gameViewModel.showResetDialog.collectAsState()
    val showGame by gameViewModel.showGame.collectAsState()
    val showSudoku by gameViewModel.showSudoku.collectAsState()
    val showNonogram by gameViewModel.showNonogram.collectAsState()
    val showWinScreen by gameViewModel.showWinScreen.collectAsState()
    val showLoss by gameViewModel.showLossScreen.collectAsState()
    val showManual by gameViewModel.showManual.collectAsState()

    val startupDismiss = {
        if (tamagotchiState.initial) {
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
                dismissOnBackPress = !tamagotchiState.initial,
                dismissOnClickOutside = false,
                usePlatformDefaultWidth = false
            )
        ) { onDismissRequest ->
            StartupDialog(
                tamagotchiState,
                { gameViewModel.submitStepsGoal(it) },
                { gameViewModel.updateBedTime(it) },
                { gameViewModel.updateWakeTime(it) },
                { gameViewModel.updatePuzzleDifficulty(it) },
                { gameViewModel.updateGameDifficulty(it) },
                onDismissRequest = onDismissRequest,
            )
        }

        DialogBase(
            tamagotchiState.hasEvolved,
            onDismissRequest = { gameViewModel.onDismissEvolution() }) { onDismissRequest ->
            EvolutionDialog(
                tamagotchiState,
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
                tamagotchiState,
            ) { score, game -> gameViewModel.gameScore(score, game) }
        }

        val puzzleModifier =
            if (showWinScreen) Modifier.shadow(5.dp, shape = RoundedCornerShape(10)) else Modifier
        DialogBase(
            showSudoku,
            modifier = puzzleModifier,
            onDismissRequest = { gameViewModel.onDismissIntelligence() }) { onDismissRequest ->

            val missingDigits = tamagotchiState.puzzleDifficulty.sudokuDigits
            val sudokuViewModel: SudokuViewModel = viewModel(
                factory = SudokuViewModelFactory(missingDigits)
            )

            SudokuDialog(
                tamagotchiState = tamagotchiState,
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
                    tamagotchiState.puzzleDifficulty.nonogramOdds
                )
            )
            NonogramDialog(
                tamagotchiState,
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

        Manual(showManual, tamagotchiState) { gameViewModel.onDismissManual() }
    }
}