package com.example.tamagotchi.utils

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tamagotchi.CHANNEL_ID
import com.example.tamagotchi.MyApp
import com.example.tamagotchi.R

@SuppressLint("MissingPermission")
fun showNotification(content: String) {
    val builder = NotificationCompat.Builder(MyApp.Companion.instance, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setContentTitle("Tamagotchi")
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(MyApp.Companion.instance)) {
        notify(1, builder.build())
    }
}