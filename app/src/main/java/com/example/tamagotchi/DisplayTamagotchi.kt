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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TamogatchiDisplay(modifier: Modifier = Modifier){
    Row(
        Modifier
            .clip(RoundedCornerShape(32.dp))
            .background(color = colorResource(R.color.lcd))
            .border(width=2.dp, color= colorResource(R.color.black),shape= RoundedCornerShape(32.dp))
            .wrapContentSize()
            .padding(32.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.tamagotchi),
            contentDescription = null,
            modifier = Modifier.size(200.dp)
        )
    }
}

@Composable
fun StatusBar(
    label: String
){
    Row(modifier = Modifier
        .fillMaxWidth()
        .height(32.dp)
        .border(width=2.dp, color=colorResource(R.color.black))
        .background(color=Color(0xFF3BBF3B))){

    }
    Text(
        text = label,
        color = colorResource(R.color.white),
        textAlign = TextAlign.Left
    )
}

@Composable
fun StatusBars(modiifer: Modifier = Modifier){
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 32.dp),
        horizontalAlignment = Alignment.Start,
    ) {
        StatusBar(stringResource(R.string.hunger))
        Spacer(modifier = Modifier.height(32.dp))
        StatusBar(stringResource(R.string.discipline))
        Spacer(modifier = Modifier.height(32.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ){
            Text(text = "9 yr", color = colorResource(R.color.white))
            Image(
                painter = painterResource(R.drawable.smiley_face),
                contentDescription = "Your Tamagotchi is happy, good job"
            )
            Text(text = "99 Ib",color = colorResource(R.color.white))
        }
    }
}