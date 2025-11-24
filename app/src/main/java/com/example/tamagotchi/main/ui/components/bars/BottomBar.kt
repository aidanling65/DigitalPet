package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
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
                    BarButton(
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