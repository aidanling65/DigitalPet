package com.example.tamagotchi

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.unit.dp
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tamagotchi.ui.theme.TamagotchiTheme
import java.time.LocalTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TamagotchiRepository(applicationContext)
        val gameViewModel = GameViewModel(repository)

        val periodicWorkRequest = PeriodicWorkRequestBuilder<TamagotchiWork>(
            15, TimeUnit.MINUTES
        )
            .setInitialDelay(2, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniquePeriodicWork(
            "tamagotchi_passive_tasks",
            ExistingPeriodicWorkPolicy.KEEP,
            periodicWorkRequest
        )

        val evolutionRequest = OneTimeWorkRequestBuilder<EvolutionWork>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()

        WorkManager.getInstance(this.applicationContext).enqueueUniqueWork(
            "evolve",
            ExistingWorkPolicy.KEEP,
            evolutionRequest
        )

        enableEdgeToEdge()
        setContent {
            TamagotchiTheme {
                TamagotchiApp(gameViewModel)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TamagotchiApp(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.purple_700)),
        topBar = { TamagotchiAppBar() },
        bottomBar = { BottomNavBar(gameViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.purple_700))
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(Modifier.height(16.dp))
            TamagotchiDisplay(tamagotchiState)
            Spacer(Modifier.height(16.dp))
            StatusBars(tamagotchiState)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TamagotchiAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            titleContentColor = colorResource(R.color.gold),
            containerColor = colorResource(R.color.purple_700)
        ),
        title = {
            Text(
                text = stringResource(R.string.tamagotchi),
                style = MaterialTheme.typography.titleLarge
            )
        },
    )
}