package com.example.digitalpet.intelligence

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.digitalpet.main.data.model.PetState
import com.example.digitalpet.main.ui.ConfettiSource
import com.example.digitalpet.main.ui.ConfettiView
import com.example.digitalpet.main.ui.components.PetDisplay
import com.example.digitalpet.main.ui.components.VisualNoise
import com.example.digitalpet.main.ui.components.dialogs.DialogBase

@Composable
fun PuzzleWinDialog(
    visible: Boolean,
    message: String,
    petState: PetState,
    onDismissRequest: () -> Unit,
    correctPuzzle: @Composable () -> Unit,
) {
    DialogBase(visible, modifier = Modifier.zIndex(2f), onDismissRequest = onDismissRequest) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10))
                .fillMaxWidth(0.9f)
                .wrapContentHeight()
                .background(MaterialTheme.colorScheme.background)
        ) {
            VisualNoise()
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                PetDisplay(petState, modifier = Modifier.weight(0.2f))
                Text(
                    message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(Modifier.weight(0.05f))
                Box(Modifier.weight(0.4f)) {
                    correctPuzzle()
                }
                Button(onClick = onDismissRequest, Modifier.weight(0.1f).fillMaxWidth(0.4f)) {
                    Image(
                        Icons.Default.Check,
                        contentDescription = "Check",
                        modifier = Modifier.padding(end = 4.dp)
                    )
                }
            }
        }
        ConfettiView(
            source = ConfettiSource.TOP,
            quantity = 100,
            modifier = Modifier.zIndex(1f)
        )
    }
}