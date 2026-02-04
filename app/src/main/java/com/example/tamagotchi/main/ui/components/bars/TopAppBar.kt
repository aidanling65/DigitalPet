package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.menu.MinimalDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    tamagotchiState: TamagotchiState,
    showManual: Boolean,
    onManualClicked: () -> Unit,
    onStartupOpen: () -> Unit,
    pauseGame: () -> Unit,
    onResetClicked: () -> Unit
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = colorResource(R.color.gold),
            containerColor = Color.Transparent
        ),
        title = {
            Text(
                text = stringResource(R.string.tamagotchi),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            MinimalDropDownMenu(
                tamagotchiState,
                showManual,
                { onManualClicked() },
                { onStartupOpen() },
                { pauseGame() },
                { onResetClicked() }
            )
        },
        modifier = Modifier.wrapContentHeight(),
    )
}