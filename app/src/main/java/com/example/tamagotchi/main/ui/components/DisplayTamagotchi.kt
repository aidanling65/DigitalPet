package com.example.tamagotchi.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState
import kotlinx.coroutines.delay

@Composable
fun TamagotchiDisplay(currentState: TamagotchiState, modifier: Modifier = Modifier) {
    var currentFrame by remember(currentState.currentAnimation){mutableStateOf(0)}

    LaunchedEffect(currentState.currentAnimation) {
        currentFrame = 0
        while(true){
            delay(500)
            currentFrame = ((currentFrame + 1) % currentState.currentAnimation.size)
        }
    }

    Row(
        Modifier
            .clip(RoundedCornerShape(48.dp))
            .border(
                width = 2.dp,
                color = colorResource(R.color.black),
                shape = RoundedCornerShape(48.dp)
            )
            .background(
                color = if (
                    currentState.currentAnimation == currentState.animations.lightsOutAwake ||
                    currentState.currentAnimation == currentState.animations.lightsOutSleep
                ) colorResource(R.color.black)
                else colorResource(R.color.lcd),
                shape = RoundedCornerShape(48.dp)
            )
            .padding(32.dp)
    ) {
        Box(
            Modifier
                .background(colorResource(R.color.lcd))
                .wrapContentSize()
        ) {
            Image(
                painter = painterResource(currentState.currentAnimation[currentFrame]),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
            )
            if (currentState.poop) {
                Image(
                    painter = painterResource(R.drawable.poop0),
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset((-10).dp, (-10).dp)
                        .size(128.dp)
                )
            }
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

