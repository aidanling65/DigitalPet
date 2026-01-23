package com.example.tamagotchi.main.ui.components.dialogs

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
    content: @Composable (onDismissRequest: () -> Unit) -> Unit
) {
    var animationVisible by remember { mutableStateOf(false) }
    LaunchedEffect(visible){
        Log.d("DialogBase", "visibility changed")
        if(visible){
            delay(50)
            animationVisible = true
        }
    }

    LaunchedEffect(animationVisible) {
        if(!animationVisible) {
            delay(150)
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
                enter = slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialOffsetY = {fullHeight -> 3*fullHeight}
                ),
                exit = slideOutVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    targetOffsetY = { fullHeight -> -2 * fullHeight }
                )
            ) {
                Box(modifier= Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    content() {
                        animationVisible = false
                    }
                }
            }
        }
    }
}