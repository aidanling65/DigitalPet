package com.example.digitalpet.main.utils

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.digitalpet.main.MainActivity
import com.example.digitalpet.R

const val CHANNEL_ID = "Tamagotchi"
const val NOTIFICATION_PERMISSION_CODE = 100
const val EVOLVE_ID = 0
const val ATTENTION_ID = 1

@SuppressLint("MissingPermission")
fun showNotification(context: Context, text: String, id: Int = ATTENTION_ID) {

    val intent = Intent(context, MainActivity::class.java).apply{
        flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
    }
    val pendingIntentFlag = PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    val pendingIntent: PendingIntent =
        PendingIntent.getActivity(context, 0, intent, pendingIntentFlag)

    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.mipmap.ic_launcher_foreground)
        .setContentTitle("Tamagotchi")
        .setContentText(text)
        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .setOnlyAlertOnce(true)

    with(NotificationManagerCompat.from(context)) {
        notify(id, builder.build())
    }
}

fun attentionNotification(context: Context, text: String){
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val isShowing = notificationManager.activeNotifications.any { it.id == ATTENTION_ID }
    var contentText = text
    if(isShowing){
        contentText == "Your tamagotchi needs attention"
    }

    showNotification(context, contentText, ATTENTION_ID)
}

fun cancelNotifications(context: Context){
    with(NotificationManagerCompat.from(context)){
        cancelAll()
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