package com.example.tamagotchi.main.ui.components.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.PopupProperties
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.GameViewModel

@Composable
fun MinimalDropDownMenu(gameViewModel: GameViewModel) {
    var expanded by remember { mutableStateOf(false) }
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()

    Box {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                Icons.Default.Menu,
                contentDescription = "Menu"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            properties = PopupProperties(clippingEnabled = true),
            modifier = Modifier
                .background(MaterialTheme.colorScheme.secondary)
                .clip(RoundedCornerShape(20))
        ) {
            DropdownMenuItem(
                text = {
                    MenuEntry(
                        imageId = R.drawable.question_mark,
                        text = stringResource(R.string.manual)
                    )
                },
                onClick = {
                    expanded = false
                    gameViewModel.onManualClicked()
                }
            )
            DropdownMenuItem(
                text = {
                    MenuEntry(
                        imageId = R.drawable.question_mark,
                        text = stringResource(R.string.settings)
                    )
                },
                onClick = {
                    expanded = false
                    gameViewModel.onStartupOpen()
                }
            )
            DropdownMenuItem(
                text = {
                    MenuEntry(
                        imageId = R.drawable.question_mark,
                        text = if (tamagotchiState.paused) stringResource(R.string.unpause) else stringResource(
                            R.string.pause
                        )
                    )
                },
                onClick = {
                    gameViewModel.pauseGame()
                }
            )
            DropdownMenuItem(
                text = {
                    MenuEntry(
                        imageId = R.drawable.reset_button,
                        text = stringResource(R.string.reset)
                    )
                },
                onClick = {
                    expanded = false
                    gameViewModel.onResetClicked()
                }
            )
        }
    }
}