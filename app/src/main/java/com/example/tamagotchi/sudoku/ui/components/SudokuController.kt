package com.example.tamagotchi.sudoku.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.main.ui.theme.TamagotchiTheme
import com.example.tamagotchi.sudoku.ui.SudokuViewModel

@Composable
fun SudokuController(
    onNumberClick: (Int) -> Unit,
    onDeleteClick: () -> Unit,
    onNoteClick: () -> Unit
){
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            (1..9).forEach { number ->
                Button(
                    onClick = { onNumberClick(number) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 3.dp),
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = number.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = onNoteClick
            ) {
                Text(
                    "Notes",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Button(
                onClick = onDeleteClick,
            ) {
                Text(
                    "Delete",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SudokuControllerPreview() {
    TamagotchiTheme {
        SudokuScreen(
            viewModel = SudokuViewModel(),
            onCellTouched = { _, _ -> }
        )
    }
}