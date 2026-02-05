package com.example.tamagotchi.intelligence.nonogram.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tamagotchi.R
import com.example.tamagotchi.intelligence.MistakeMeter
import com.example.tamagotchi.intelligence.PuzzleLossDialog
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.intelligence.nonogram.NonogramView
import com.example.tamagotchi.intelligence.nonogram.NonogramViewModel
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun NonogramDialog(
    tamagotchiState: TamagotchiState,
    nonogramViewModel: NonogramViewModel,
    showWin: Boolean,
    onWin: () -> Unit,
    showLoss: Boolean,
    onLoss: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nonogram = nonogramViewModel.nonogramGame
    var blocking by remember { mutableStateOf(false) }

    LaunchedEffect(nonogram.won.value) {
        if (nonogram.won.value) {
            onWin()
        }
    }
    if (showWin) {
        PuzzleWinDialog(
            true,
            "Congratulations!\nYou solved the Nonogram!",
            tamagotchiState
        ) {
            onDismissRequest()
        }
    }

    LaunchedEffect(nonogram.mistakes.value) {
        if (nonogram.mistakes.value >= tamagotchiState.difficulty.mistakes) {
            onLoss()
        }
    }
    if (showLoss) {
        PuzzleLossDialog(
            true,
            "Too bad\nYou failed the Nonogram!",
            tamagotchiState
        ) { onDismissRequest() }
    }

    Column(
        modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
            .fillMaxHeight(0.9f)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Nonogatchi",
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(R.color.gold),
        )

        TamagotchiDisplay(tamagotchiState, modifier = Modifier.weight(0.1f))
        MistakeMeter(nonogram.mistakes.value, tamagotchiState.difficulty.mistakes)
        NonogramView(nonogram, modifier = Modifier.weight(0.5f), blocking)
        BottomButtons(blocking) { blocking = !blocking }
        Spacer(modifier = Modifier.weight(0.05f))
    }
}


@Preview(showBackground = true)
@Composable
fun NonogramDialogPreview() {
    val nonogramViewModel: NonogramViewModel = viewModel()
    NonogramDialog(TamagotchiState(), nonogramViewModel,true, {}, false, {}, {})
}
