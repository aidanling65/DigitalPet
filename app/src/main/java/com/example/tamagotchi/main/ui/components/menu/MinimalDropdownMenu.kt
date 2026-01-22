package com.example.tamagotchi.main.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.example.tamagotchi.main.ui.GameViewModel
import kotlinx.coroutines.delay

@Composable
fun MinimalDropDownMenu(gameViewModel: GameViewModel) {
    var expanded by remember { mutableStateOf(false) }
    var expandedMenu by remember { mutableStateOf(false) }
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    var visibleIndex by remember { mutableIntStateOf(-1) }
    val showManual by gameViewModel.showManual.collectAsState()


    LaunchedEffect(expanded) {
        if (expanded) {
            expandedMenu = true
            while (visibleIndex < 3) {
                delay(100)
                visibleIndex++
            }
        } else {
            while (visibleIndex > -1) {
                delay(100)
                visibleIndex--
            }
            delay(400)
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
                contentDescription = "Menu"
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
                    gameViewModel.onManualClicked()
                }
            )
            MenuEntry(
                imageId = R.drawable.settings,
                text = stringResource(R.string.settings),
                visibleIndex >= 1,
                showManual,
                onClick = {
                    gameViewModel.onStartupOpen()
                }
            )
            MenuEntry(
                imageId = R.drawable.question_mark,
                text = if (tamagotchiState.paused) stringResource(R.string.unpause) else stringResource(
                    R.string.pause
                ),
                visibleIndex >= 2,
                showManual,
                onClick = {
                    gameViewModel.pauseGame()
                },
            )
            MenuEntry(
                imageId = R.drawable.reset_button,
                text = stringResource(R.string.reset),
                visibleIndex >= 3,
                showManual,
                onClick = {
                    gameViewModel.onResetClicked()
                }
            )
        }
    }
}