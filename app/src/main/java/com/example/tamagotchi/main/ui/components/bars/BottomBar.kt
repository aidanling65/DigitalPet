package com.example.tamagotchi.main.ui.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomBar(
    feed: () -> Unit,
    light: () -> Unit,
    clean: () -> Unit,
    heal: () -> Unit,
    play: () -> Unit,
    intelligence: () -> Unit,
    discipline: () -> Unit
) {
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.surface,
        contentPadding = PaddingValues(bottom = 8.dp, top = 4.dp),
        modifier = Modifier
            .height(64.dp),
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    Triple(R.drawable.knife_fork, { feed()}, R.string.feed),
                    Triple(R.drawable.light_bulb, { light() }, R.string.light),
                    Triple(R.drawable.clean, { clean() }, R.string.clean),
                    Triple(R.drawable.heal, { heal() }, R.string.heal),
                    Triple(R.drawable.play, { play() }, R.string.play),
                    Triple(R.drawable.intelligence, { intelligence() }, R.string.learning),
                    Triple(R.drawable.discipline, { discipline() }, R.string.discipline)
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

@Preview(showBackground = true)
@Composable
fun BottomBarPreview() {
    BottomBar({},{},{},{},{},{},{})
}