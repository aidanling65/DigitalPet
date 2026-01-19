package com.example.tamagotchi.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun TamagotchiDisplay(currentState: TamagotchiState, modifier: Modifier = Modifier) {

    Row(
        modifier
            .clip(RoundedCornerShape(percent = 25))
            .border(
                width = 2.dp,
                color = colorResource(R.color.black),
                shape = RoundedCornerShape(25)
            )
            .background(
                color = if (
                    currentState.currentAnimation == currentState.animations.lightsOutAwake ||
                    currentState.currentAnimation == currentState.animations.lightsOutSleep
                ) colorResource(R.color.black)
                else colorResource(R.color.lcd),
                shape = RoundedCornerShape(percent = 25)
            )
            .padding(8.dp)
    ) {
        Box(
            Modifier
                .background(colorResource(R.color.lcd))
                .wrapContentSize()
                .aspectRatio(1f)
        ) {
            AnimateDrawable(
                drawableRes=currentState.currentAnimation,
                modifier = Modifier.fillMaxSize()
            )
            if (currentState.poop) {
                BoxWithConstraints(modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxSize(0.7f)) {

                    val offsetX = maxWidth * -0.05f
                    val offsetY = maxHeight * -0.05f
                    Image(
                        painter = painterResource(R.drawable.poop0),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(offsetX,offsetY)
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}