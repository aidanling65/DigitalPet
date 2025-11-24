package com.example.tamagotchi.main.utils

import com.example.tamagotchi.R

fun loadAnimations(prefix: String) : List<Int>{
    val drawableFields = R.drawable::class.java.declaredFields
    return drawableFields
        .filter { it.name.startsWith(prefix)  }
        .mapNotNull {
            try {
                it.getInt(null )
            } catch(e: Exception){
                null
            }
        }
}