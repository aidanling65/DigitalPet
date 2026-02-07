package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.data_logging.google.InteractionType
import com.example.tamagotchi.data_logging.google.LoadingState
import com.example.tamagotchi.theme.DialogColor

@Composable
fun LoadingDialog(loadingState: LoadingState, interactionType: InteractionType, onDismissRequest:() -> Unit) {
    Column(
        Modifier
            .clip(RoundedCornerShape(20))
            .fillMaxHeight(0.3f)
            .fillMaxWidth(0.6f)
            .background(DialogColor),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (loadingState == LoadingState.LOADING) {
            CircularProgressIndicator()
        }

        Spacer(Modifier.height(16.dp))

        Text(text = loadingState.message(interactionType), textAlign = TextAlign.Center)
        AnimatedVisibility(
            loadingState != LoadingState.LOADING,
            enter = expandVertically { -it }) {
            Button(onClick = onDismissRequest) {
                Text("Dismiss")
            }
        }
    }
}