package com.example.tamagotchi.main.ui.components.manual

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.components.manual.contents.CleanContent
import com.example.tamagotchi.main.ui.components.manual.contents.DeathContent
import com.example.tamagotchi.main.ui.components.manual.contents.EvolutionContent
import com.example.tamagotchi.main.ui.components.manual.contents.HappinessContent
import com.example.tamagotchi.main.ui.components.manual.contents.HungerContent
import com.example.tamagotchi.main.ui.components.manual.contents.MistakeContent
import com.example.tamagotchi.main.ui.components.manual.contents.SickContent
import com.example.tamagotchi.main.ui.components.manual.contents.SleepContent
import kotlinx.coroutines.delay

@Composable
fun ManualDrawer(onDismissRequest: () -> Unit) {
    var visible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(16)
        visible = true
    }

    Dialog(
        onDismissRequest = { visible = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false
        )
    ) {
        Row(
            Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            AnimatedVisibility(
                visible = visible,
                enter = slideInHorizontally(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                ) { fullWidth -> -fullWidth },
                exit = slideOutHorizontally(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessHigh
                    )
                ) { fullWidth -> -fullWidth }
            ) {
                DisposableEffect(Unit) {
                    onDispose {
                        if (!visible) {
                            onDismissRequest()
                        }
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp))
                        .background(MaterialTheme.colorScheme.background)
                        .padding(16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    item { Spacer(modifier = Modifier.height(16.dp)) }
                    item { Text("Manual", style = MaterialTheme.typography.headlineMedium) }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.mistakes),
                            content = {
                                MistakeContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.evolution),
                            content = {
                                EvolutionContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.death),
                            content = {
                                DeathContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.happiness),
                            painterId = R.drawable.play,
                            content = {
                                HappinessContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.hunger),
                            painterId = R.drawable.knife_fork,
                            content = {
                                HungerContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.discipline),
                            painterId = R.drawable.discipline,
                            content = {
                                Text(
                                    text = stringResource(R.string.discipline_description),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(
                                        top = 4.dp,
                                        start = 8.dp,
                                        bottom = 8.dp
                                    ),
                                    color = MaterialTheme.colorScheme.background,
                                    lineHeight = 16.sp
                                )
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.intelligence),
                            painterId = R.drawable.intelligence,
                            content = {
                                Text(
                                    text = stringResource(R.string.intelligence_description),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(
                                        top = 4.dp,
                                        start = 8.dp,
                                        bottom = 8.dp
                                    ),
                                    color = MaterialTheme.colorScheme.background,
                                    lineHeight = 16.sp
                                )
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.fitness),
                            content = {
                                Text(
                                    text = stringResource(R.string.fitness_description),
                                    style = MaterialTheme.typography.bodySmall,
                                    modifier = Modifier.padding(
                                        top = 4.dp,
                                        start = 8.dp,
                                        bottom = 8.dp
                                    ),
                                    color = MaterialTheme.colorScheme.background,
                                    lineHeight = 16.sp
                                )
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.sleep),
                            painterId = R.drawable.light_bulb,
                            content = {
                                SleepContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.cleanliness),
                            painterId = R.drawable.clean,
                            content = {
                                CleanContent()
                            }
                        )
                    }
                    item {
                        ManualEntry(
                            title = stringResource(R.string.sickness),
                            painterId = R.drawable.heal,
                            content = {
                                SickContent()
                            }
                        )
                    }
                }
            }
        }
    }
}