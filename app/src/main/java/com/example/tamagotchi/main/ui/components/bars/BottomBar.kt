package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.GameViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(gameViewModel: GameViewModel) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentPadding = PaddingValues(bottom=8.dp,top=0.dp),
        modifier = Modifier.height(56.dp),
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    Triple(R.drawable.knife_fork, { gameViewModel.feed() }, R.string.feed),
                    Triple(R.drawable.light_bulb, { gameViewModel.light() }, R.string.light),
                    Triple(R.drawable.clean, { gameViewModel.clean() }, R.string.clean),
                    Triple(R.drawable.heal, { gameViewModel.heal() }, R.string.heal),
                    Triple(R.drawable.play, { gameViewModel.play() }, R.string.play),
                    Triple(
                        R.drawable.intelligence,
                        { gameViewModel.launchSudoku() },
                        R.string.learning
                    ),
                    Triple(
                        R.drawable.discipline,
                        { gameViewModel.discipline() },
                        R.string.discipline
                    )
                ).forEach { (icon, onClick, desc) ->
                    BarButton(
                        painterId = icon,
                        onClick = onClick,
                        contentDescription = stringResource(desc),
                        modifier = Modifier
                            .weight(1f)
                    )
                }
            }
        },
    )
}