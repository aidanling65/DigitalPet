package com.example.tamagotchi.main.ui.components.dialogs.startup

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun UpDownButtons(
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
){

    val incInteractionSource = remember { MutableInteractionSource() }
    val isIncrementing by incInteractionSource.collectIsPressedAsState()

    val decInteractionSource = remember { MutableInteractionSource() }
    val isDecrementing by decInteractionSource.collectIsPressedAsState()


    LaunchedEffect(isIncrementing) {
        var delay = 250L
        if(isIncrementing){
            Log.d("UpDownButtons", "isIncrementing")
            while(isIncrementing){
                onIncrement()
                delay(delay)
                if(delay > 100L) delay -= 5L
            }
        }
    }
    LaunchedEffect(isDecrementing) {
        var delay = 250L
        if(isDecrementing){
            Log.d("UpDownButtons", "isDecrementing")
            while(isDecrementing){
                onDecrement()
                delay(delay)
                if(delay > 100L) delay -= 5L
            }
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = onIncrement,
            interactionSource = incInteractionSource,
            modifier = Modifier
                .clip(RoundedCornerShape(30))
                .background(MaterialTheme.colorScheme.secondary)
                .aspectRatio(2f)
                .weight(1f)
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Increment Step Goal",
                tint = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(Modifier.height(2.dp))
        IconButton(
            onClick = onDecrement,
            interactionSource = decInteractionSource,
            modifier = Modifier
                .clip(RoundedCornerShape(30))
                .background(MaterialTheme.colorScheme.secondary)
                .aspectRatio(2f)
                .weight(1f)
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrement Step Goal",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}