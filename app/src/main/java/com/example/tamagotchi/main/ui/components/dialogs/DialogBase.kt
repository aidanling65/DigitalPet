package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay

@Composable
fun DialogBase(
    visible: Boolean,
    onDismissRequest: () -> Unit,
    properties: DialogProperties = DialogProperties(
        dismissOnBackPress = true,
        dismissOnClickOutside = false,
        usePlatformDefaultWidth = false
    ),
    content: @Composable () -> Unit
) {
    var animationVisible by remember { mutableStateOf(false) }
    LaunchedEffect(visible){
        if(visible){
            delay(200)
            animationVisible = true
        }
    }

    LaunchedEffect(animationVisible) {
        if(!animationVisible) {
            delay(500)
            onDismissRequest()
        }
    }

    if(visible || animationVisible) {
        Dialog(
            onDismissRequest = {animationVisible = false},
            properties = properties
        ) {
            AnimatedVisibility(
                visible = animationVisible,
                enter = fadeIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ),
                exit = fadeOut(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
            ) {
                content()
            }
        }
    }
}