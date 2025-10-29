package com.example.tamagotchi

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.tamagotchi.ui.theme.TamagotchiTheme
import java.util.concurrent.TimeUnit


const val NOTIFICATION_PERMISSION_CODE = 100
const val CHANNEL_ID = "Tamagotchi"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TamagotchiRepository(MyApp.instance)
        val gameViewModel = GameViewModel(repository)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATION_PERMISSION_CODE
            )
        createNotificationChannel()

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
            .setInitialDelay(5, TimeUnit.SECONDS)
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

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.tamagotchi)
            val descriptionText = getString(R.string.tamagotchi_notifications)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system.
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}

@Composable
fun TamagotchiApp(gameViewModel: GameViewModel, modifier: Modifier = Modifier) {
    val tamagotchiState by gameViewModel.tamagotchiState.collectAsState()
    val showDialog by gameViewModel.showResetDialog.collectAsState()

    if(showDialog){
        ResetDialog(
            onDismissRequest = {gameViewModel.onDismissDialog()},
            onConfirmation = {gameViewModel.confirmReset() }
        )
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        topBar = { TamagotchiAppBar(gameViewModel) },
        bottomBar = { BottomNavBar(gameViewModel) }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
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