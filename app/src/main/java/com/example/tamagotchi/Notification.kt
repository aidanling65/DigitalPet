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
        /*if (ActivityCompat.checkSelfPermission(
                MyApp.instance,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }*/
        notify(1, builder.build())
    }
}