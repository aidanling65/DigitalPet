package com.example.tamagotchi.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.TamagotchiState

@Composable
fun TamagotchiDisplay(currentState: TamagotchiState, modifier: Modifier = Modifier) {

    Box(modifier) {
        Box(
            Modifier
                .clip(RoundedCornerShape(percent = 25))
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.black),
                    shape = RoundedCornerShape(25)
                )
                .background(colorResource(R.color.lcd))
                .wrapContentSize()
                .aspectRatio(1f)
        ) {
            AnimateDrawable(
                drawableRes = currentState.currentAnimation,
                modifier = Modifier.fillMaxSize()
            )
            if (currentState.poop) {
                BoxWithConstraints(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .fillMaxSize(0.7f)
                ) {

                    val offsetX = maxWidth * -0.05f
                    val offsetY = maxHeight * -0.05f
                    Image(
                        painter = painterResource(R.drawable.poop0),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(offsetX, offsetY)
                            .fillMaxSize()
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = currentState.paused,
            enter = slideInVertically(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ) { fullHeight -> -fullHeight },
            exit = fadeOut(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioNoBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(percent = 25))
                    .background(
                        Color.Black.copy(alpha = 0.5f),
                    )
                    .aspectRatio(1f)
                    .zIndex(2f)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.paused),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    filterQuality = FilterQuality.None,
                )
            }
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TamagotchiDisplayPreview() {
    TamagotchiDisplay(TamagotchiState(paused = true))
}