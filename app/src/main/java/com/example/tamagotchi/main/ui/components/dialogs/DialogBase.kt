package com.example.tamagotchi.main.ui.components.dialogs

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

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
    AnimatedVisibility(
        visible = visible,
        enter = fadeIn(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessLow
            )
        ),
        exit = fadeOut(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioNoBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = properties
        ) {
            content()
        }
    }
}