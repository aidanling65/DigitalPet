package com.example.tamagotchi.intelligence.nonogram

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamagotchi.R
import com.example.tamagotchi.intelligence.PuzzleWinDialog
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay

@Composable
fun NonogramDialog(
    tamagotchiState: TamagotchiState,
    showWin: Boolean,
    onWin: () -> Unit,
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier
) {
    val nonogram = remember {
        NonogramBoard(10, 10)
    }

    val gridWidth = nonogram.width
    val gridHeight = nonogram.height

    val columnHints = nonogram.getColumnHints()
    val rowHints = nonogram.getRowHints()

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

    Column(
        modifier
            .clip(RoundedCornerShape(10))
            .fillMaxWidth()
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
        Spacer(Modifier.fillMaxHeight(0.05f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.weight(0.2f)
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(0.2f)
                )
                LazyRow(
                    modifier = Modifier
                        .weight(1f)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(
                        count = columnHints.size,
                        key = { index -> index }
                    ) { index ->
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(8.dp)
                                .border(
                                    1.dp,
                                    Color.Transparent
                                )
                                .weight(0.8f)
                        ) {
                            Text(
                                text = columnHints[index].joinToString("\n"),
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                lineHeight = 12.sp,
                                modifier = Modifier.padding(bottom = 2.dp)
                            )
                        }
                    }
                }
            }
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(0.2f)
                        .aspectRatio(0.2f / gridHeight * gridWidth),
                    columns = GridCells.Fixed(1),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    userScrollEnabled = false
                ) {
                    itemsIndexed(
                        items = List(gridHeight) { it },
                        key = { index, _ -> index }
                    ) { index, _ ->
                        Box(
                            contentAlignment = Alignment.CenterEnd,
                            modifier = Modifier
                                .fillMaxHeight()
                                .background(MaterialTheme.colorScheme.secondary)
                                .padding(4.dp)
                                .border(
                                    1.dp,
                                    Color.Transparent
                                )
                                .weight(0.8f)
                        ) {
                            Text(
                                text = rowHints[index].joinToString(" "),
                                fontSize = 12.sp,
                                lineHeight = 14.sp
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .border(2.dp, MaterialTheme.colorScheme.secondary)
                        .pointerInput(nonogram) {
                            var lastCellIndex: Int? = null
                            detectDragGestures(
                                onDrag = { change, _ ->
                                    Log.d("Nonogram", "Here")
                                    val localPosition = change.position
                                    val itemSize = size.width / nonogram.width
                                    val col = (localPosition.x / itemSize).toInt()
                                        .coerceIn(0, nonogram.width - 1)
                                    val row = (localPosition.y / itemSize).toInt()
                                        .coerceIn(0, nonogram.height - 1)
                                    val cellIndex = row * nonogram.width + col
                                    if (cellIndex != lastCellIndex) {
                                        if (blocking) {
                                            nonogram.blockCell(row, col)
                                        } else {
                                            nonogram.clickCell(row, col)
                                        }
                                    }
                                    lastCellIndex = cellIndex
                                },
                                onDragStart = { offset ->
                                    val itemSize = size.width / nonogram.width
                                    val col = (offset.x / itemSize).toInt()
                                        .coerceIn(0, nonogram.width - 1)
                                    val row = (offset.y / itemSize).toInt()
                                        .coerceIn(0, nonogram.height - 1)
                                    val cellIndex = row * nonogram.width + col
                                    if (cellIndex != lastCellIndex) {
                                        if (blocking) {
                                            nonogram.blockCell(row, col)
                                        } else {
                                            nonogram.clickCell(row, col)
                                        }
                                    }

                                    lastCellIndex = cellIndex
                                }
                            )
                        },
                    columns = GridCells.Fixed(nonogram.width),
                    userScrollEnabled = false
                ) {
                    itemsIndexed(
                        items = List(gridWidth * gridHeight) { it },
                        key = { index, _ -> index }
                    ) { index, _ ->
                        val row = index / gridWidth
                        val col = index % gridHeight
                        val cell = nonogram.playerCells[row][col]
                        val color =
                            if (cell.value && nonogram.correctCells[row][col]) {
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
                                    if ((col + 1) % 5 == 0 && col + 1 < nonogram.width) {
                                        verticalWidth = 3.dp.toPx()
                                    }
                                    if ((row + 1) % 5 == 0 && row + 1 < nonogram.height) {
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
                                .clickable(
                                    enabled = true,
                                    onClick = {
                                        if (blocking) {
                                            nonogram.blockCell(row, col)
                                        } else {
                                            nonogram.clickCell(row, col)
                                        }
                                    },
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            if (nonogram.blockedCells[row][col].value) {
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
}

@Preview(showBackground = true)
@Composable
fun NonogramDialogPreview() {
    NonogramDialog(TamagotchiState(), false, {}, {})
}
