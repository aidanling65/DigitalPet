package com.example.tamagotchi.main

import android.Manifest
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.ExistingWorkPolicy
import com.example.tamagotchi.main.data.repository.TamagotchiRepository
import com.example.tamagotchi.main.domain.workers.utils.createSingleWorker
import com.example.tamagotchi.main.ui.GameViewModel
import com.example.tamagotchi.main.ui.components.TamagotchiApp
import com.example.tamagotchi.main.utils.NOTIFICATION_PERMISSION_CODE
import com.example.tamagotchi.main.utils.cancelNotifications
import com.example.tamagotchi.main.utils.createNotificationChannel
import com.example.tamagotchi.step_tracker.StepCounterWorker
import com.example.tamagotchi.theme.TamagotchiTheme
import kotlinx.coroutines.runBlocking
import java.time.Duration


class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                    val repository = TamagotchiRepository(applicationContext)
                    @Suppress("UNCHECKED_CAST")
                    return GameViewModel(application, repository) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACTIVITY_RECOGNITION, Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATION_PERMISSION_CODE
        )
        createNotificationChannel(applicationContext)


        enableEdgeToEdge()
        setContent {
            TamagotchiTheme {
                TamagotchiApp(
                    gameViewModel,
                )
            }
        }
    }

    override fun onRestart(){
        super.onRestart()
        createSingleWorker<StepCounterWorker>(
            applicationContext,
            Duration.ZERO,
            "step_worker_temp",
            ExistingWorkPolicy.REPLACE
        )
    }

    override fun onResume(){
        super.onResume()
        cancelNotifications(applicationContext)
        Log.d("MainActivity", "onResume")
        gameViewModel.checkEvolve()
        runBlocking {
            gameViewModel.fetchHistory()
        }
    }


    override fun onStop(){
        super.onStop()
        cancelNotifications(applicationContext)
        gameViewModel.uploadHistory()
    }



}