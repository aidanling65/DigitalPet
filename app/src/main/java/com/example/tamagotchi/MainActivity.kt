package com.example.tamagotchi

import android.Manifest
import android.Manifest.permission.ACTIVITY_RECOGNITION
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.domain.workers.scheduleEvolutionWork
import com.example.tamagotchi.ui.GameViewModel
import com.example.tamagotchi.ui.components.TamagotchiApp
import com.example.tamagotchi.ui.theme.TamagotchiTheme
import com.example.tamagotchi.utils.createNotificationChannel
import com.example.tamagotchi.utils.showNotification
import kotlinx.coroutines.launch

const val NOTIFICATION_PERMISSION_CODE = 100

class MainActivity : ComponentActivity() {

    private val gameViewModel: GameViewModel by viewModels {
        object : ViewModelProvider.Factory{
            override fun<T: ViewModel> create(modelClass: Class<T>): T{
                val repository = TamagotchiRepository(applicationContext)
                return GameViewModel(applicationContext, repository) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(ACTIVITY_RECOGNITION,Manifest.permission.POST_NOTIFICATIONS),
            NOTIFICATION_PERMISSION_CODE
        )
        createNotificationChannel(applicationContext)

        enableEdgeToEdge()
        setContent {
            TamagotchiTheme {
                TamagotchiApp(gameViewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        gameViewModel.startStepCounter()
    }
}