package com.example.digitalpet.intelligence.nonogram

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.digitalpet.intelligence.nonogram.ui.ColumnHints
import com.example.digitalpet.intelligence.nonogram.ui.NonogramGrid
import com.example.digitalpet.intelligence.nonogram.ui.RowHints

@Composable
fun NonogramView(nonogram: NonogramBoard, modifier: Modifier = Modifier, blocking: Boolean){
    val gridHeight = nonogram.height
    val gridWidth = nonogram.width
    val columnHints = nonogram.columnHints
    val rowHints = nonogram.rowHints

    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ColumnHints(columnHints, modifier = Modifier.weight(0.2f))

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .weight(1f)
        ) {
            RowHints(gridHeight, gridWidth, rowHints, modifier = Modifier.weight(0.2f))
            NonogramGrid(nonogram, gridWidth, gridHeight, blocking, Modifier.weight(1f))
        }
    }
}