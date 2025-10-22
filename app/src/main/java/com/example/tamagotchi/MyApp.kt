package com.example.tamagotchi

import android.app.Application

class MyApp : Application() {
    companion object{
        lateinit var instance: MyApp
            private set

        lateinit var currentAnimation: List<Int>
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}