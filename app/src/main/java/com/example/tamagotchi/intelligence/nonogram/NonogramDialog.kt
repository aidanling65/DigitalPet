package com.example.tamagotchi.intelligence.nonogram

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun NonogramDialog(
    tamagotchiState: TamagotchiState,
    gameViewModel: GameViewModel,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nonogram = remember {
        NonogramBoard(
            "#---------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------\n" +
                    "----------"
        )
    }

    val gridWidth = nonogram.width + 1
    val gridHeight = nonogram.height + 1

    var blocking by remember { mutableStateOf(false) }
    val showWin = gameViewModel.showWinScreen.collectAsState()

    if(showWin.value) {
        PuzzleWinDialog(
            showWin.value,
            "Congratulations!\nYou solved the Nonogram!",
            tamagotchiState
        ) {
            onDismissRequest()
        }
    }

    LaunchedEffect(nonogram.won.value) {
        if(nonogram.won.value){
            gameViewModel.showWinScreen()
            gameViewModel.learning()
        }
    }

    Column(
        modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth(0.95f)
            .fillMaxHeight(0.9f)
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Nonogatchi",
            style = MaterialTheme.typography.titleLarge,
            color = colorResource(R.color.gold)
        )
        TamagotchiDisplay(tamagotchiState, modifier = Modifier.fillMaxWidth(0.4f))
        Row(
            modifier = Modifier.padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LazyVerticalGrid(
                modifier = Modifier.weight(1f),
                columns = GridCells.Fixed(nonogram.width + 1),
            ) {
                itemsIndexed(
                    items = List(gridWidth * gridHeight) { it },
                    key = { index, _ -> index }
                ) { index, _ ->
                    val row = index / gridWidth
                    val col = index % gridHeight
                    when {
                        row == 0 && col == 0 -> {
                            Spacer(modifier = Modifier.aspectRatio(1f))
                        }

                        row == 0 -> {
                            Text(
                                text = nonogram.getColumnHints()[col - 1].joinToString("\n"),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(2.dp)
                            )
                        }

                        col == 0 -> {
                            Text(
                                text = nonogram.getRowHints()[row - 1].joinToString(" "),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall,
                                modifier = Modifier.padding(2.dp)
                            )
                        }

                        else -> {
                            val dataRow = row - 1
                            val dataCol = col - 1
                            val cell = nonogram.playerCells[dataRow][dataCol]
                            val color =
                                if (cell.value && nonogram.correctCells[dataRow][dataCol]) {
                                    Color.Black
                                } else if (cell.value) {
                                    Color.Red
                                } else {
                                    Color.White
                                }

                            val animatedColor by animateColorAsState(
                                targetValue = color,
                                label = "cell color",
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            )

                            val secondaryColor = MaterialTheme.colorScheme.secondary
                            Box(
                                modifier = Modifier
                                    .background(animatedColor)
                                    .drawBehind {
                                        var verticalWidth = 1.dp.toPx()
                                        var horizontalWidth = 1.dp.toPx()
                                        if ((dataCol + 1) % 5 == 0 && dataCol + 1 < nonogram.width) {
                                            verticalWidth = 3.dp.toPx()
                                        }
                                        if ((dataRow + 1) % 5 == 0 && dataRow + 1 < nonogram.height) {
                                            horizontalWidth = 3.dp.toPx()
                                        }
                                        val x = size.width - verticalWidth / 2
                                        val y = size.height - horizontalWidth / 2
                                        drawLine(
                                            color = secondaryColor,
                                            start = Offset(x, 0f),
                                            end = Offset(x, size.height),
                                            strokeWidth = verticalWidth
                                        )
                                        drawLine(
                                            color = secondaryColor,
                                            start = Offset(0f, y),
                                            end = Offset(size.width, y),
                                            strokeWidth = horizontalWidth
                                        )
                                    }
                                    .aspectRatio(1f)
                                    .clickable(enabled = true, onClick = {
                                        if (blocking) {
                                            nonogram.blockCell(dataRow, dataCol)
                                        } else {
                                            nonogram.clickCell(dataRow, dataCol)
                                        }
                                    }),
                                contentAlignment = Alignment.Center
                            ) {
                                if (nonogram.blockedCells[dataRow][dataCol].value) {
                                    Image(
                                        imageVector = Icons.Default.Clear,
                                        contentDescription = "Clear",
                                        modifier = Modifier.fillMaxSize()
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
        Button(
            onClick = { blocking = !blocking },
            modifier = Modifier.fillMaxWidth(0.4f)
        ) {
            Text(
                text = if (blocking) "Fill" else "Block",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

/*@Preview(showBackground = false)
@Composable
fun NonogramPreview() {
    NonogramDialog(TamagotchiState(), GameViewModel(LocalContext.))
}*/