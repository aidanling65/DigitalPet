package com.example.digitalpet.main.ui.components.settings

import android.util.Log
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalpet.theme.DigitalPetTheme
import kotlinx.coroutines.delay

@Composable
fun UpDownButtons(
    onDecrement: () -> Unit,
    onIncrement: () -> Unit,
    modifier: Modifier = Modifier
) {

    val incInteractionSource = remember { MutableInteractionSource() }
    val isIncrementing by incInteractionSource.collectIsPressedAsState()

    val decInteractionSource = remember { MutableInteractionSource() }
    val isDecrementing by decInteractionSource.collectIsPressedAsState()


    LaunchedEffect(isIncrementing) {
        var delay = 250L
        Log.d("UpDownButtons", "isIncrementing")
        while (isIncrementing) {
            onIncrement()
            delay(delay)
            if (delay > 100L) delay -= 5L
        }
    }
    LaunchedEffect(isDecrementing) {
        var delay = 250L
        Log.d("UpDownButtons", "isDecrementing")
        while (isDecrementing) {
            onDecrement()
            delay(delay)
            if (delay > 100L) delay -= 5L
        }
    }

    Column(
        modifier = modifier
            .padding(horizontal = 4.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Button(
            onClick = onIncrement,
            interactionSource = incInteractionSource,
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = (-2).dp
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(2f)
        ) {
            Icon(
                Icons.Default.KeyboardArrowUp,
                contentDescription = "Increment Step Goal",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(Modifier.height(2.dp))
        Button(
            onClick = onDecrement,
            interactionSource = decInteractionSource,
            shape = RoundedCornerShape(30),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 0.dp
            ),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .weight(1f)
                .aspectRatio(2f)
        ) {
            Icon(
                Icons.Default.KeyboardArrowDown,
                contentDescription = "Decrement Step Goal",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun UpDownButtonPreview(){
    DigitalPetTheme {
        UpDownButtons(
            {},
            {}
        )
    }
}