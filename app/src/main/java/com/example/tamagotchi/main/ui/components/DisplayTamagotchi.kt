package com.example.tamagotchi.main.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import com.example.tamagotchi.main.utils.animation.AnimateDrawable
import com.example.tamagotchi.main.utils.animation.AnimateDrawableOneShot

@Composable
fun TamagotchiDisplay(
    currentState: TamagotchiState,
    modifier: Modifier = Modifier,
    onAnimationFinish: () -> Unit = {},
    showEatingAnimation: Boolean = false
) {
    Box(modifier) {
        Box(
            Modifier
                .clip(RoundedCornerShape(percent = 25))
                .border(
                    width = 2.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(25)
                )
                .background(colorResource(R.color.lcd))
                .aspectRatio(1f)
        ) {
            AnimateDrawable(
                drawableRes = currentState.currentAnimation,
                modifier = Modifier.fillMaxSize()
            )

            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .fillMaxSize(0.7f)
            ) {
                AnimatedVisibility(
                    visible = currentState.poop,
                    enter = fadeIn(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMediumLow
                        )
                    ),
                    exit = fadeOut(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessVeryLow
                        )
                    )
                ) {
                    Image(
                        painter = painterResource(R.drawable.poop0),
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset((-20).dp, 0.dp)
                            .fillMaxSize()
                    )
                }
                AnimatedVisibility(
                    visible = showEatingAnimation,
                    enter = fadeIn()
                ) {
                    AnimateDrawableOneShot(
                        drawableRes = R.drawable.eating_bread,
                        key = showEatingAnimation,
                        modifier = Modifier
                            .align(Alignment.BottomEnd)
                            .offset(80.dp, (-16).dp)
                            .size(50.dp),
                        onAnimationFinish
                    )
                }
            }
            AnimatedVisibility(
                visible = currentState.paused,
                modifier = Modifier.zIndex(2f),
                enter = fadeIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow
                    )
                ),
                exit = fadeOut(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                        )
                        .fillMaxSize()
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
            AnimatedVisibility(
                visible = currentState.lightAnimationState == 1,
                modifier = Modifier.zIndex(3f),
                enter = fadeIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
            ) {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                        .zIndex(3f)
                )
            }
            AnimatedVisibility(
                visible = currentState.lightAnimationState == 2,
                modifier = Modifier.zIndex(3f),
                enter = fadeIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessHigh
                    )
                ),
                exit = fadeOut(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow
                    )
                )
            ){
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxSize()
                        .zIndex(3f)
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