package com.example.tamagotchi.minigame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun GameDialog(
    tamagotchiState: TamagotchiState,
    onDismissRequest: () -> Unit,
    modifier: Modifier
) {
    Dialog(
        onDismissRequest = onDismissRequest,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = true
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .clip(RoundedCornerShape(10))
                .background(MaterialTheme.colorScheme.background)
        ) {
            Box(
                Modifier
                    .clip(RoundedCornerShape(10))
                    .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                    .background(colorResource(R.color.lcd))
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
            ) {
                PlatformerGameCanvas(tamagotchiState, Modifier)
            }
        }
    }
}