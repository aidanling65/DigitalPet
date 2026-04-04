package com.example.digitalpet.intelligence.nonogram.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import com.example.digitalpet.intelligence.nonogram.NonogramBoard

@Composable
fun NonogramGrid(
    nonogram: NonogramBoard,
    gridWidth: Int,
    gridHeight: Int,
    blocking: Boolean,
    modifier: Modifier = Modifier
) {
    val currentBlocking by rememberUpdatedState(blocking)

    LazyVerticalGrid(
        modifier = modifier
            .shadow(8.dp)
            .aspectRatio(1f)
            .border(2.dp, MaterialTheme.colorScheme.secondary)
            .pointerInput(nonogram) {
                var lastCellIndex: Int? = null
                var lastRow: Int? = null
                var lastCol: Int? = null
                detectDragGestures(
                    onDrag = { change, _ ->
                        val localPosition = change.position
                        val itemSize = size.width / nonogram.width
                        val col = (localPosition.x / itemSize).toInt()
                            .coerceIn(0, nonogram.width - 1)
                        val row = (localPosition.y / itemSize).toInt()
                            .coerceIn(0, nonogram.height - 1)
                        val cellIndex = row * nonogram.width + col
                        if (cellIndex != lastCellIndex) {
                            if (currentBlocking) {
                                if (lastCellIndex == null || nonogram.blockedCells[row][col].value != nonogram.blockedCells[lastRow!!][lastCol!!].value) {
                                    nonogram.blockCell(row, col)
                                }
                            } else {
                                if (lastCellIndex == null || nonogram.playerCells[row][col].value != nonogram.playerCells[lastRow!!][lastCol!!].value) {
                                    nonogram.clickCell(row, col)
                                }
                            }
                        }
                        lastCellIndex = cellIndex
                        lastRow = row
                        lastCol = col
                    },
                    onDragStart = { offset ->
                        val itemSize = size.width / nonogram.width
                        val col = (offset.x / itemSize).toInt()
                            .coerceIn(0, nonogram.width - 1)
                        val row = (offset.y / itemSize).toInt()
                            .coerceIn(0, nonogram.height - 1)
                        val cellIndex = row * nonogram.width + col
                        if (cellIndex != lastCellIndex) {
                            if (currentBlocking) {
                                if (lastCellIndex == null || nonogram.blockedCells[row][col].value != nonogram.blockedCells[lastRow!!][lastCol!!].value) {
                                    nonogram.blockCell(row, col)
                                }
                            } else {
                                if (lastCellIndex == null || nonogram.playerCells[row][col].value != nonogram.playerCells[lastRow!!][lastCol!!].value) {
                                    nonogram.clickCell(row, col)
                                }
                            }

                        }

                        lastCellIndex = cellIndex
                        lastRow = row
                        lastCol = col
                    },
                    onDragEnd = {
                      lastCellIndex = null
                      lastRow = null
                      lastCol = null
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