package com.example.tamagotchi.utils

import com.example.tamagotchi.MyApp

fun loadAnimations(prefix: String) : List<Int>{
    val animations = mutableListOf<Int>()
    val context = MyApp.Companion.instance
    var index = 0
    while(true){
        val frame = context.resources.getIdentifier(
            "${prefix}$index",
            "drawable",
            context.packageName
        )
        if(frame == 0) break
        else{
            animations.add(frame)
        }
        index++
    }

    return animations
}