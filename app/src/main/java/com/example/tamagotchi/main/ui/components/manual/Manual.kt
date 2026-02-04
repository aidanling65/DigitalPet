package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.TamagotchiDisplay
import kotlinx.coroutines.delay

@Composable
fun Manual(showManual: Boolean, tamagotchiState: TamagotchiState, onDismissRequest: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(showManual) {
        if (showManual) {
            delay(50)
            visible = true
        }
    }

    LaunchedEffect(visible) {
        if (!visible) {
            delay(200)
            onDismissRequest()
        }
    }

    if (showManual) {
        Dialog(
            onDismissRequest = { visible = false },
            properties = DialogProperties(
                usePlatformDefaultWidth = false,
                decorFitsSystemWindows = false,
            )
        ) {
            (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0f)
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
                    exit = slideOutHorizontally(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessLow
                        ),
                    ) { fullWidth -> 2 * fullWidth }
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
                            .background(MaterialTheme.colorScheme.background)
                            .padding(12.dp),
                    ) {
                        Row(
                            Modifier
                                .height(64.dp)
                                .padding(top = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Box(Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.CenterStart) {
                                Text(
                                    stringResource(R.string.manual),
                                    style = MaterialTheme.typography.headlineMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            Box(Modifier.fillMaxSize().weight(1f), contentAlignment = Alignment.CenterEnd) {
                                TamagotchiDisplay(tamagotchiState)
                            }
                        }
                        ManualColumn()
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ManualPreview() {
    Manual(true, TamagotchiState(), {})
}