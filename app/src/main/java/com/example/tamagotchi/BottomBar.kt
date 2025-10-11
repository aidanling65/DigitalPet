package com.example.tamagotchi

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

@Composable
fun NavButton(painter: Painter,
              contentDescription: String,
              onClick: () -> Unit= {},
              modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(48.dp)
            .clickable(onClick=onClick)
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
fun BottomNavBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        containerColor = colorResource(R.color.blue),
        actions = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                listOf(
                    R.drawable.kife_fork to R.string.feed,
                    R.drawable.light_bulb to R.string.light,
                    R.drawable.clean to R.string.clean,
                    R.drawable.heal to R.string.heal,
                    R.drawable.play to R.string.play,
                    R.drawable.discipline to R.string.discipline
                ).forEach { (icon, desc) ->
                    NavButton(
                        painter = painterResource(icon),
                        contentDescription = stringResource(desc),
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                    )
                }}
        },
    )
}