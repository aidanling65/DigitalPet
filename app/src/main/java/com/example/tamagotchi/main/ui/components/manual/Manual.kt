package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.main.ui.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun Manual(gameViewModel: GameViewModel) {
    val showManual by gameViewModel.showManual.collectAsState()
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(showManual) {
        if(showManual) {
            delay(50)
            visible = true
        }
    }

    LaunchedEffect(visible) {
        if(!visible){
            delay(200)
            gameViewModel.onDismissManual()
        }
    }

    if(showManual) {
        Dialog(
            onDismissRequest = { visible = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false
            )
        ) {
            Box(
                Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.CenterStart
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = slideInHorizontally(
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioNoBouncy,
                                    stiffness = Spring.StiffnessLow
                                )
                            ) { fullWidth -> 2 * fullWidth },
                    exit =  slideOutHorizontally(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    ) { fullWidth -> 2 * fullWidth }
                ) {
                    ManualColumn()
                }
            }
        }
    }
}