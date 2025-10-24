package com.example.tamagotchi

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TamagotchiDisplay(currentState: TamagotchiState, modifier: Modifier = Modifier) {
    var currentFrame by remember(MyApp.currentAnimation){mutableStateOf(0)}

    LaunchedEffect(MyApp.currentAnimation) {
        currentFrame = 0
        while(true){
            delay(500)
            currentFrame = ((currentFrame + 1) % MyApp.currentAnimation.size)
        }
    }

    Row(
        Modifier
            .clip(RoundedCornerShape(48.dp))
            .background(color = colorResource(R.color.lcd))
            .border(
                width = 2.dp,
                color = colorResource(R.color.black),
                shape = RoundedCornerShape(48.dp)
            )
            .wrapContentSize()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(MyApp.currentAnimation[currentFrame]),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
        if(currentState.poop){
            Image(
                painter = painterResource(R.drawable.poop0),
                contentDescription = null
            )
        }
    }
}

/*@Composable
fun PlayAnimation(animation: Int){
    val img = findViewById<ImageView>(animation)
    img.setBackgroundResource(animation)

    val frameAnimation = img.background as AnimationDrawable
    frameAnimation.start()
}*/

@Composable
fun StatusBar(
    progress: Int,
    maximum: Int,
    label: String
) {
    val progressBar: Float = progress.toFloat() / maximum
    val color = when {
        progressBar <= 0.5f -> {
            val factor = progressBar / 0.5f
            Color(
                red = 1f,
                green = factor,
                blue = 0f
            )
        }

        else -> {
            val factor = (progressBar - 0.5f) / 0.5f
            Color(
                red = 1f - factor,
                green = 1f,
                blue = 0f
            )
        }
    }

    LinearProgressIndicator(
        progress = { progressBar },
        color = color,
        trackColor = Color(0x00000000),
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
            .border(
                width = 2.dp,
                color = colorResource(R.color.black),
                shape = RoundedCornerShape(16.dp)
            )
    )
    Text(
        text = label,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Left,
        style = MaterialTheme.typography.bodySmall
    )
}

@Composable
fun StatusBars(
    tamagotchiState: TamagotchiState,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        val spacerModifier = Modifier.height(16.dp)
        StatusBar(tamagotchiState.hunger, MAX_HUNGER, stringResource(R.string.hunger))
        Spacer(modifier = spacerModifier)
        StatusBar(tamagotchiState.discipline, MAX_DISCIPLINE, stringResource(R.string.discipline))
        Spacer(modifier = spacerModifier)
        StatusBar(tamagotchiState.happiness, MAX_HAPPINESS, stringResource(R.string.happiness))
        Spacer(modifier = spacerModifier)
        Text(
            text = "State: ${tamagotchiState.ageStage.name}",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column() {
                Text(
                    text = "${tamagotchiState.age} yr",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.misbehaving) stringResource(R.string.misbehaving) else stringResource(
                        R.string.well_behaved
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.light) stringResource(R.string.lights_on) else stringResource(
                        R.string.lights_out
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
            /*Image(
                painter = painterResource(R.drawable.smiley_face),
                contentDescription = "Your Tamagotchi is happy, good job",
                contentScale = ContentScale.Fit
            )*/
            Column()
            {
                Text(
                    text = "${tamagotchiState.weight} Ib",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.poop) stringResource(R.string.dirty) else stringResource(
                        R.string.clean
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = if (tamagotchiState.sleeping) stringResource(R.string.sleeping) else stringResource(
                        R.string.awake
                    ),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}