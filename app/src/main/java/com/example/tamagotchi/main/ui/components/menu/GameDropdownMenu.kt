package com.example.tamagotchi.main.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import kotlinx.coroutines.delay

@Composable
fun GameDropDownMenu(
    tamagotchiState: TamagotchiState,
    showManual: Boolean,
    onManualClicked : () ->Unit,
    onStartupOpen: () -> Unit,
    pauseGame: () -> Unit,
    onResetClicked: () -> Unit,
    onStatsClicked: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    var visibleIndex by remember { mutableIntStateOf(-1) }

    val onDismiss = { expanded = false }

    val maxIndex = 4
    LaunchedEffect(expanded) {
        if (expanded) {
            expandedMenu = true
            while (visibleIndex < maxIndex) {
                if (visibleIndex < 0) {
                    delay(50)
                } else {
                    delay(100)
                }
                visibleIndex++
            }
        } else {
            while (visibleIndex > -1) {
                if (visibleIndex < maxIndex) {
                    delay(100)
                }
                visibleIndex--
            }
            delay(300)
            expandedMenu = false
        }
    }

    Box(
        modifier = Modifier
            .background(Color(0x00000000))
            .zIndex(2f)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu",
                modifier = Modifier.fillMaxHeight(),
                tint = MaterialTheme.colorScheme.primary
            )
        }
        DropdownMenu(
            expanded = expandedMenu,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .clip(RectangleShape)
                .clip(
                    RoundedCornerShape(
                        topStart = 20f,
                        bottomStart = 20f,
                        topEnd = 0f,
                        bottomEnd = 0f
                    )
                ),
            containerColor = Color.Transparent,
            shadowElevation = 0.dp,
            offset = DpOffset(x = 60.dp, y = 0.dp),
        ) {
            MenuEntry(
                imageId = R.drawable.question_mark,
                text = stringResource(R.string.manual),
                visible = visibleIndex >= 0,
                showManual,
                onClick = {
                    onManualClicked()
                },
                onDismiss
            )
            MenuEntry(
                imageId = R.drawable.settings,
                text = stringResource(R.string.settings),
                visibleIndex >= 1,
                showManual,
                onClick = {
                    onStartupOpen()
                },
                onDismiss
            )
            MenuEntry(
                imageId = if (tamagotchiState.paused) R.drawable.play_button else R.drawable.pause_button,
                text = if (tamagotchiState.paused) stringResource(R.string.unpause) else stringResource(
                    R.string.pause
                ),
                visibleIndex >= 2,
                showManual,
                onClick = {
                    pauseGame()
                },
                onDismiss
            )
            MenuEntry(
                imageId = R.drawable.stats,
                text = stringResource(R.string.stats),
                visibleIndex >= 3,
                showManual,
                onClick = {
                    onStatsClicked()
                },
                onDismiss

            )
            MenuEntry(
                imageId = R.drawable.reset_button,
                text = stringResource(R.string.reset),
                visibleIndex >= 4,
                showManual,
                onClick = {
                    onResetClicked()
                },
                onDismiss
            )
        }
    }
}