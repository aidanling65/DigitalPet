package com.example.tamagotchi.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.tamagotchi.CHANNEL_ID
import com.example.tamagotchi.MainActivity
import com.example.tamagotchi.R


@SuppressLint("MissingPermission")
fun showNotification(context:Context, text: String) {
    val contentIntent = PendingIntent.getActivity(
        context, 0,
        Intent(context, MainActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT
    )

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setContentTitle("Tamagotchi")
        .setContentIntent(contentIntent)
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)

    with(NotificationManagerCompat.from(context)) {
        notify(1, builder.build())
    }
}


fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = context.getString(R.string.tamagotchi)
        val descriptionText = context.getString(R.string.tamagotchi_notifications)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}