package com.example.tamagotchi.main.ui.components

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.tamagotchi.R
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import com.example.tamagotchi.main.data.model.TamagotchiState
import com.example.tamagotchi.main.utils.animation.AnimateDrawable
import com.example.tamagotchi.main.utils.animation.AnimateDrawableOneShot
import com.example.tamagotchi.main.utils.animation.getAnimationDuration
import kotlinx.coroutines.delay

@Composable
fun TamagotchiDisplay(
    currentState: TamagotchiState,
    modifier: Modifier = Modifier,
    onAnimationFinish: () -> Unit = {},
    showEatingAnimation: Int = 0
) {
    val eatingAnimation = listOf(R.drawable.eating_bread, R.drawable.eating_burger).random()
    val eatingDuration = getAnimationDuration(LocalContext.current, eatingAnimation)
    var tamagotchiEating by remember { mutableStateOf(false) }
    LaunchedEffect(showEatingAnimation) {
        if (showEatingAnimation > 0) {
            tamagotchiEating = true
        } else {
            delay(eatingDuration.toLong() * 2)
            tamagotchiEating = false
        }
    }

    Log.d("TamagotchiDisplay", "${currentState.currentAnimation} ${EvolutionAnimations.BABY.idle}")
    Box(
        modifier
            .clip(RoundedCornerShape(percent = 25))
            .border(
                width = 2.dp,
                color = Color.Black,
                shape = RoundedCornerShape(25)
            )
            .background(if (currentState.light) colorResource(R.color.lcd) else Color.Black)
            .aspectRatio(1f)
            .fillMaxSize()

    ) {
        BoxWithConstraints(
            Modifier
                .padding(32.dp)
                .fillMaxSize()
        ) {
            val maxWidth = maxWidth
            val maxHeight = maxHeight
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.lcd))
            ) {
                if (currentState.loading) {
                    Box(modifier = Modifier.fillMaxSize())
                } else if (tamagotchiEating) {
                    AnimateDrawable(
                        drawableRes = currentState.animations.eating
                            ?: currentState.currentAnimation,
                        modifier = Modifier
                            .fillMaxSize()
                    )
                } else {
                    val size = if (currentState.ageStage == AgeStage.EGG) 0.4f else 1f
                    val offset =
                        if (currentState.ageStage == AgeStage.EGG) (maxWidth * 0.3f) else 0.dp
                    AnimateDrawable(
                        drawableRes = currentState.currentAnimation,
                        modifier = Modifier
                            .fillMaxSize(size)
                            .offset(offset, offset)
                    )
                }
            }
            val poopOffsetX = maxWidth * 0.15f
            val poopOffsetY = maxHeight * 0.15f
            AnimatedVisibility(
                visible = currentState.poop,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(-poopOffsetX, -poopOffsetY)
                    .fillMaxSize(0.2f),
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
                val bitmap = ImageBitmap.imageResource(R.drawable.poop)
                Image(
                    bitmap = bitmap,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    filterQuality = FilterQuality.None
                )
            }
            val foodOffsetX = maxWidth * 0.15f
            val foodOffsetY = maxHeight * 0.6f
            AnimatedVisibility(
                visible = showEatingAnimation > 0,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(-foodOffsetX, -foodOffsetY)
                    .fillMaxSize(0.2f),
                enter = fadeIn(
                    animationSpec = spring(
                        stiffness = Spring.StiffnessVeryLow
                    )
                ),
                exit = ExitTransition.None
            ) {
                Box {
                    AnimateDrawableOneShot(
                        drawableRes = eatingAnimation,
                        key = showEatingAnimation,
                        modifier = Modifier.fillMaxSize(),
                        onAnimationFinish
                    )
                }
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
                        .fillMaxSize(),
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
            ),
            exit = ExitTransition.None
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            )
        }
        AnimatedVisibility(
            visible = currentState.lightAnimationState == 2,
            modifier = Modifier.zIndex(3f),
            enter = EnterTransition.None,
            exit = fadeOut(
                animationSpec = spring(
                    stiffness = Spring.StiffnessVeryLow
                )
            )
        ) {
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxSize()
            )
        }
    }
}

@Preview(showBackground = false)
@Composable
fun TamagotchiDisplayPreview() {
    TamagotchiDisplay(
        TamagotchiState(
            paused = false,
            ageStage = AgeStage.BABY,
            animations = EvolutionAnimations.CHILD,
            loading = false,
            poop = true
        ),
        showEatingAnimation = 1
    )
}

@Preview(showBackground = false)
@Composable
fun TamagotchiDisplayPreview2() {
    TamagotchiDisplay(
        TamagotchiState(
            paused = false,
            ageStage = AgeStage.CHILD,
            animations = EvolutionAnimations.CHILD,
            loading = false,
            poop = true,
            sick = false
        ),
        showEatingAnimation = 0
    )
}