package com.example.tamagotchi.intelligence.nonogram

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.intelligence.PuzzleLossDialog
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun NonogramDialog(
    tamagotchiState: TamagotchiState,
    showWin: Boolean,
    onWin: () -> Unit,
    showLoss: Boolean,
    onLoss: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nonogram = remember {
        NonogramBoard(10, 10, 0.6f)
    }
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
        if (nonogram.mistakes.value >= 5) {
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

    val bitmap = ImageBitmap.imageResource(R.drawable.heart)
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
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(5 - nonogram.mistakes.value) {
                Image(
                    bitmap = bitmap,
                    contentDescription = "lives",
                    contentScale = ContentScale.FillBounds,
                    filterQuality = FilterQuality.None,
                    modifier = Modifier
                        .size(32.dp)
                        .padding(4.dp)
                )
            }
        }
        NonogramView(nonogram, modifier = Modifier.weight(0.5f), blocking)
        BottomButtons(blocking) {blocking = !blocking}
        Spacer(modifier = Modifier.weight(0.05f))
    }
}


@Preview(showBackground = true)
@Composable
fun NonogramDialogPreview() {
    NonogramDialog(TamagotchiState(), false, {}, false, {}, {})
}
