package com.example.digitalpet.main.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.zIndex
import com.example.digitalpet.R


@Composable
fun VisualNoise(modifier: Modifier = Modifier){
    Image(
        painter = painterResource(id = R.drawable.noise),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier
            .fillMaxSize()
            .zIndex(6f)
    )
}