package com.example.digitalpet.main.ui.components.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.digitalpet.R
import com.example.digitalpet.main.ui.components.VisualNoise

@Composable
fun MenuEntry(
    imageId: Int,
    text: String,
    visible: Boolean,
    moving: Boolean,
    onClick: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    val bitmap = ImageBitmap.imageResource(imageId)
    val visible = !moving && visible

    AnimatedVisibility(
        visible = visible,
        enter = slideInHorizontally(
            animationSpec = spring(
                dampingRatio = if (moving) Spring.DampingRatioNoBouncy else Spring.DampingRatioLowBouncy,
                stiffness = if (moving) Spring.StiffnessMediumLow else Spring.StiffnessMediumLow
            ),
            initialOffsetX = { fullWidth -> if (moving) -fullWidth else 2 * fullWidth }
        ),
        exit = slideOutHorizontally(
            animationSpec = spring(
                dampingRatio = if (moving) Spring.DampingRatioNoBouncy else Spring.DampingRatioLowBouncy,
                stiffness = if (moving) Spring.StiffnessMediumLow else Spring.StiffnessMediumLow
            ),
            targetOffsetX = { fullWidth -> if (moving) -fullWidth else 2 * fullWidth }
        )
    ) {

        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    interactionSource = null,
                    indication = null,
                    enabled = true,
                    onClick = onDismiss
                )
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 50f,
                            bottomStart = 50f,
                            topEnd = 0f,
                            bottomEnd = 0f
                        )
                    )
                    .fillMaxWidth(0.5f)
            )
            {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    VisualNoise(
                        Modifier
                            .height(48.dp)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 50f,
                                    bottomStart = 50f,
                                    topEnd = 0f,
                                    bottomEnd = 0f
                                )
                            )
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .height(48.dp)
                            .shadow(
                                4.dp, RoundedCornerShape(
                                    topStart = 50f,
                                    bottomStart = 50f,
                                    topEnd = 0f,
                                    bottomEnd = 0f
                                )
                            )
                            .clip(
                                RoundedCornerShape(
                                    topStart = 50f,
                                    bottomStart = 50f,
                                    topEnd = 0f,
                                    bottomEnd = 0f
                                )
                            )
                            .background(MaterialTheme.colorScheme.secondary.copy(alpha = 0.85f))
                            .fillMaxWidth()
                            .clickable(
                                interactionSource = null,
                                enabled = true,
                                onClick = onClick
                            )
                            .padding(start = 16.dp)
                    ) {
                        Image(
                            bitmap = bitmap,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .fillMaxWidth()
                                .weight(0.25f)
                                .padding(end = 8.dp),
                            contentScale = ContentScale.Fit,
                            filterQuality = FilterQuality.None
                        )
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.75f)
                        )
                    }
                }
            }
        }
    }
}

@Preview()
@Composable
fun MenuEntryPreview() {
    MenuEntry(R.drawable.question_mark, "Question", true, false)
}