package com.example.tamagotchi.intelligence.nonogram

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun RowHints(gridHeight: Int, gridWidth:Int, rowHints:List<List<Int>>, modifier: Modifier=Modifier){
    LazyVerticalGrid(
        modifier = modifier
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
            ) {
                Text(
                    text = rowHints[index].joinToString(" "),
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}