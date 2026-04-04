package com.example.digitalpet.main.utils

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log

class StepCounter(
    context: Context,
    private val onTotalStepsReported: (Int)-> Unit
): SensorEventListener {
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

    private var initialSteps: Int? = null

    fun startListening(){
        if(sensor != null){
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI)
        } else {
            Log.w("StepCounter", "Step counter sensor not available")
        }
    }

    fun stopListening(){
        sensorManager.unregisterListener(this)
        initialSteps = null
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(event == null) return

        val totalStepsSinceReboot = event.values[0].toInt()
        onTotalStepsReported(totalStepsSinceReboot)
    }
}
