package com.example.digitalpet.main.ui.components.bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalpet.R
import com.example.digitalpet.theme.DigitalPetTheme

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
        windowInsets = WindowInsets.navigationBars,
        contentPadding = PaddingValues(0.dp),
        modifier = Modifier
            .fillMaxWidth(),
        actions = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(46.dp),
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

@Preview(showBackground = false)
@Composable
fun BottomBarPreview() {
    DigitalPetTheme{
        BottomBar({},{},{},{},{},{},{})
    }
}