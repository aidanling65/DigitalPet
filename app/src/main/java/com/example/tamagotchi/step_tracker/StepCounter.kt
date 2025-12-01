package com.example.tamagotchi.step_tracker

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

private const val TAG = "STEP_COUNT_LISTENER"

class StepCounter(private val context: Context) {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER) }

    suspend fun steps() = suspendCancellableCoroutine { continuation ->
        Log.d(TAG, "Registering sensor listener... ")

        val listener =
            object : SensorEventListener {
                override fun onSensorChanged(event: SensorEvent?) {
                    if (event == null) return

                    val stepsSinceLastReboot = event.values[0].toLong()
                    Log.d(TAG, "Steps since last reboot: $stepsSinceLastReboot")

                    if (continuation.isActive) {
                        continuation.resume(stepsSinceLastReboot)
                    }
                }

                override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                    Log.d(TAG, "Accuracy changed to: $accuracy")
                }
            }

        val supportedAndEnabled = sensorManager.registerListener(
            listener,
            sensor, SensorManager.SENSOR_DELAY_UI
        )

        if(!supportedAndEnabled){
            Log.e(TAG, "Failed to register step counter sensor listener.")
            if(continuation.isActive){
                continuation.resume(0L)
            }
            return@suspendCancellableCoroutine
        }
        Log.d(TAG, "Sensor listener registered successfully")

        continuation.invokeOnCancellation {
            Log.d(TAG, "Unregistering sensor listener.")
            sensorManager.unregisterListener(listener)
        }
    }
}
