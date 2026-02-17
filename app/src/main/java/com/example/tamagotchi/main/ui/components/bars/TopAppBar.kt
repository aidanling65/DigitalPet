package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.ui.components.menu.GameDropDownMenu

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    tamagotchiState: TamagotchiState,
    showManual: Boolean,
    onManualClicked: () -> Unit,
    onStartupOpen: () -> Unit,
    pauseGame: () -> Unit,
    onResetClicked: () -> Unit,
    onStatsClicked: () -> Unit,
) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            containerColor = MaterialTheme.colorScheme.surface
        ),
        title = {
            Text(
                text = stringResource(R.string.tamagotchi),
                style = MaterialTheme.typography.titleLarge,
                modifier=Modifier.padding(bottom=8.dp)
            )
        },
        actions = {
            GameDropDownMenu(
                tamagotchiState,
                showManual,
                { onManualClicked() },
                { onStartupOpen() },
                { pauseGame() },
                { onResetClicked() },
                {onStatsClicked()}
            )
        },
        modifier = Modifier.wrapContentHeight(),
    )
}