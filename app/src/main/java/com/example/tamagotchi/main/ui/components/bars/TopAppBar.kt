package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(gameViewModel: GameViewModel? = null,) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = colorResource(R.color.gold),
            containerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = stringResource(R.string.tamagotchi),
                style = MaterialTheme.typography.titleLarge
            )
        },
        actions = {
            if(gameViewModel != null) {
                BarButton(
                    painter = painterResource(R.drawable.reset_button),
                    contentDescription = stringResource(R.string.reset_tamagotchi),
                    onClick = { gameViewModel.onResetClicked() },
                    modifier = Modifier
                )
            }
        }
    )
}