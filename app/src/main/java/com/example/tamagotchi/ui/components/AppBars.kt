package com.example.tamagotchi.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.ui.GameViewModel
import com.example.tamagotchi.R

@Composable
fun NavButton(painter: Painter,
              contentDescription: String,
              onClick: () -> Unit= {},
              modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable(onClick = onClick)
            .padding(4.dp),
        contentAlignment = Alignment.Center
    ){
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Fit
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TamagotchiAppBar(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
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
            NavButton(
                painter=painterResource(R.drawable.reset_button),
                contentDescription = stringResource(R.string.reset_tamagotchi),
                onClick = {gameViewModel.onResetClicked()},
                modifier = Modifier
            )
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    Triple(R.drawable.kife_fork, {gameViewModel.feed()}, R.string.feed),
                    Triple(R.drawable.light_bulb, {gameViewModel.light()},  R.string.light),
                    Triple(R.drawable.clean, {gameViewModel.clean()}, R.string.clean),
                    Triple(R.drawable.heal, {gameViewModel.heal()}, R.string.heal),
                    Triple(R.drawable.play, {gameViewModel.play()},  R.string.play),
                    Triple(R.drawable.discipline, {gameViewModel.discipline()}, R.string.discipline)
                ).forEach{(icon, onClick, desc) ->
                    NavButton(
                        painter = painterResource(icon),
                        onClick = onClick,
                        contentDescription = stringResource(desc),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }}
        },
    )
}