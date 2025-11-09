package com.example.tamagotchi

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.app.ActivityCompat
import com.example.tamagotchi.data.repository.TamagotchiRepository
import com.example.tamagotchi.ui.GameViewModel
import com.example.tamagotchi.ui.components.TamagotchiApp
import com.example.tamagotchi.ui.theme.TamagotchiTheme
import com.example.tamagotchi.utils.createNotificationChannel

const val NOTIFICATION_PERMISSION_CODE = 100
const val CHANNEL_ID = "Tamagotchi"

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val repository = TamagotchiRepository(applicationContext)
        val gameViewModel = GameViewModel(repository)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
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
}