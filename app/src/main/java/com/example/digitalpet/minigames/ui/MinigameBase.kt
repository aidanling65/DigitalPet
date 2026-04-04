package com.example.digitalpet.minigames.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.digitalpet.R

@Composable
fun MinigameBase(
    title: String,
    score: Int,
    isGameStarted: Boolean,
    isGameOver: Boolean,
    isGameOverScreen: Boolean,
    countdown: Int,
    restart: () -> Unit,
    jumpAction: () -> Unit,
    content: @Composable (modifier: Modifier) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        ScoreBoard(score, isGameStarted && !isGameOver)
        Box(
            Modifier
                .clip(RoundedCornerShape(10))
                .border(2.dp, colorResource(R.color.black), RoundedCornerShape(10))
                .background(colorResource(R.color.lcd))
                .fillMaxWidth(0.9f)
                .aspectRatio(1f)
        ) {
            content(Modifier.matchParentSize())
            Box(
                modifier = Modifier.matchParentSize(),
                contentAlignment = Alignment.Center
            ) {
                GameOverScreen(isGameOverScreen, score)
                Countdown(isGameStarted, countdown)
            }
        }
    }
    Spacer(modifier = Modifier.height(16.dp))
    GameButtons(isGameOverScreen, isGameStarted, { restart() }, jumpAction = { jumpAction() })
}