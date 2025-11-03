package com.example.tamagotchi

import android.annotation.SuppressLint
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

@SuppressLint("MissingPermission")
fun showNotification(content: String) {
    val builder = NotificationCompat.Builder(MyApp.instance, CHANNEL_ID)
        .setSmallIcon(R.drawable.tamagotchi)
        .setContentTitle("Tamagotchi")
        .setContentText(content)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(MyApp.instance)) {
        notify(1, builder.build())
    }
}