package com.example.tamagotchi.main.data.model

import com.example.tamagotchi.R
import com.example.tamagotchi.main.utils.loadAnimations

enum class EvolutionAnimations(
    val idle: List<Int>,
    val eating: List<Int>?,
    val sleep: List<Int>?,
    val lightsOutSleep: List<Int>?,
    val lightsOutAwake: List<Int>?,
    val sick: List<Int>?,
    val play: List<Int>,
) {
    EGG(
        idle = loadAnimations("egg_idle"),
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = null,
        play = loadAnimations("egg_idle")
    ),
    BABY(
        idle = loadAnimations("baby_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("baby_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("baby_sick"),
        play = loadAnimations("baby_play")
    ),
    CHILD(
        idle = loadAnimations("child_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("child_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("child_sick"),
        play = loadAnimations("child_play")
    ),
    TEEN_1(
        idle = loadAnimations("teen_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_1_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("teen_1_sick"),
        play = loadAnimations("teen_1_play")
    ),
    TEEN_2(
        idle = loadAnimations("teen_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_2_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("teen_2_sick"),
        play = loadAnimations("teen_2_play")
    ),
    ADULT_1(
        idle = loadAnimations("adult_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_1_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_1_sick"),
        play = loadAnimations("adult_1_play")
    ),
    ADULT_2(
        idle = loadAnimations("adult_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_2_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_2_sick"),
        play = loadAnimations("adult_2_play")
    ),
    ADULT_3(
        idle = loadAnimations("adult_3_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_3_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_3_sick"),
        play = loadAnimations("adult_3_play")
    ),
    ADULT_4(
        idle = loadAnimations("adult_4_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_4_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_4_sick"),
        play = loadAnimations("adult_4_play")
    ),
    ADULT_5(
        idle = loadAnimations("adult_5_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_5_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_5_sick"),
        play = loadAnimations("adult_5_play")
    ),
    ADULT_6(
        idle = loadAnimations("adult_6_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_6_sleep"),
        lightsOutSleep = loadAnimations("lights_out_sleep"),
        lightsOutAwake = listOf(R.drawable.lights_out_awake),
        sick = loadAnimations("adult_6_sick"),
        play = loadAnimations("adult_6_play")
    ),
    DEAD(
        idle = loadAnimations("dead"),
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = null,
        sick = null,
        play = loadAnimations("dead")
    );
}