package com.example.tamagotchi.data.model

import com.example.tamagotchi.R
import com.example.tamagotchi.utils.loadAnimations

enum class EvolutionAnimations(
    val idle: List<Int>,
    val eating: List<Int>?,
    val sleep: List<Int>?,
    val lightsOutSleep: List<Int>?,
    val lightsOutAwake: List<Int>?,
    val sick: List<Int>?
) {
    EGG(
        idle = loadAnimations("egg_idle"),
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = null,
    ),
    BABY(
        idle = loadAnimations("baby_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("baby_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    CHILD(
        idle = loadAnimations("child_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("child_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    TEEN_1(
        idle = loadAnimations("teen_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_1_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    TEEN_2(
        idle = loadAnimations("teen_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_2_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_1(
        idle = loadAnimations("adult_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_1_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_2(
        idle = loadAnimations("adult_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_2_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_3(
        idle = loadAnimations("adult_3_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_3_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_4(
        idle = loadAnimations("adult_4_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_4_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_5(
        idle = loadAnimations("adult_5_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_5_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_6(
        idle = loadAnimations("adult_6_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_6_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = listOf(R.drawable.tamagotchi)
    ),
    DEAD(
        idle = loadAnimations("dead"),
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = null,
        sick = null
    );
}