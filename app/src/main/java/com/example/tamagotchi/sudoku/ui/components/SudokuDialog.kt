package com.example.tamagotchi.sudoku.ui.components

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.sudoku.ui.SudokuViewModel

@Composable
fun SudokuDialog(
    viewModel: SudokuViewModel,
    tamagotchiState: TamagotchiState,
    onDismissRequest: () -> Unit,
    onGameWon: () -> Unit
) {
    val isGameWon by viewModel.sudokuGame.gameWonLiveData.observeAsState(initial = false)
    val context = LocalContext.current

    LaunchedEffect(isGameWon) {
        if (isGameWon) {
            Toast.makeText(context, "Congratulations! You solved it!", Toast.LENGTH_SHORT).show()
            onGameWon()
        }
    }
    Dialog(onDismissRequest = onDismissRequest, properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = false, usePlatformDefaultWidth = false)){
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .fillMaxWidth(0.95f)
                .fillMaxHeight(0.9f)
        ) {
            SudokuScreen(
                viewModel = viewModel,
                tamagotchiState = tamagotchiState,
                onCellTouched = { row, col -> viewModel.sudokuGame.updateSelectedCell(row, col) }
            )
        }
    }
}