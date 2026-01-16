package com.example.tamagotchi.main.ui.components.manual.contents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.ui.components.status_bars.StatusBar
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun HungerContent() {
    var progress by remember{ mutableStateOf(2)}
    val maximum = 4

    LaunchedEffect(Unit) {
        while(true){
            val progressChange = Random.nextInt(-1, 2)
            progress = (progress + progressChange).coerceIn(0, maximum)
            delay(500)
        }
    }

    Column(
        modifier = Modifier.padding(top = 4.dp, start = 8.dp, end = 8.dp, bottom=8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.hunger_description),
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(
                top = 4.dp,
                start = 8.dp,
            ),
            color = Color.Black,
            lineHeight = 16.sp
        )
        StatusBar(progress = progress, maximum = maximum)
    }
}