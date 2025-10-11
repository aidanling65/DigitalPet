package com.example.tamagotchi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.ui.theme.TamagotchiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TamagotchiTheme {
                TamagotchiApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TamagotchiAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = colorResource(R.color.gold),
            containerColor = colorResource(R.color.blue)
        ),
        title = {
            Text(
                text = stringResource(R.string.tamagotchi),
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(modifier: Modifier = Modifier) {
    BottomAppBar(
        actions = {
            Button(
                onClick = {},
                content = { Text(text = "Food") }
            )
            Button(
                onClick = {},
                content = { Text(text = "Light") }
            )
            Button(
                onClick = {},
                content = { Text(text = "Clean") }
            )
            Button(
                onClick = {},
                content = { Text(text = "Heal") }
            )
            Button(
                onClick = {},
                content = { Text(text = "Play") }
            )
            Button(
                onClick = {},
                content = { Text(text = "Discipline") }
            )
        },
    )
}

@Composable
fun TamagotchiApp(modifier: Modifier = Modifier) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blue)),
        topBar = { TamagotchiAppBar() },
        bottomBar = { BottomNavBar() }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.blue))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(64.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(color = colorResource(R.color.lcd))
                    .weight(1.5f)
                    .padding(32.dp)
            ) {
                Image(
                    painter = painterResource(R.drawable.tamagotchi),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
            Text(
                text = stringResource(R.string.hunger),
                modifier = Modifier.weight(1F),
                color = colorResource(R.color.white)
            )
            Text(
                text = stringResource(R.string.discipline),
                modifier = Modifier.weight(1F),
                color = colorResource(R.color.white)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TamagotchiTheme {
        TamagotchiApp()
    }
}