package com.example.tamagotchi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tamagotchi.ui.theme.TamagotchiTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TamagotchiRepository(this.applicationContext)
        val gameViewModel = GameViewModel(repository)
        enableEdgeToEdge()
        setContent {
            TamagotchiTheme {
                TamagotchiApp(gameViewModel)
            }
        }
    }
}

@Composable
fun TamagotchiApp(gameViewModel: GameViewModel, modifier: Modifier=Modifier) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.blue)),
        topBar = { TamagotchiAppBar() },
        bottomBar = { BottomNavBar(gameViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()

                .background(color = colorResource(R.color.blue))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(Modifier.height(16.dp))
            TamogatchiDisplay()
            Spacer(Modifier.height(16.dp))
            StatusBars(gameViewModel, tamagotchiState)
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