package com.example.tamagotchi.main.data.model

import com.example.tamagotchi.R

enum class EvolutionAnimations(
    val idle: Int,
    val eating: Int?,
    val sleep: Int?,
    val lightsOutSleep: Int?,
    val lightsOutAwake: Int?,
    val sick: Int?,
    val play: Int,
) {
    EGG(
        idle = R.drawable.egg,
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = null,
        play = R.drawable.egg
    ),
    BABY(
        idle = R.drawable.baby_idle,
        eating = R.drawable.baby_eating,
        sleep = R.drawable.baby_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.baby_sick,
        play = R.drawable.baby_play
    ),
    CHILD(
        idle = R.drawable.child_idle,
        eating = R.drawable.child_eating,
        sleep = R.drawable.child_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.child_sick,
        play = R.drawable.child_play
    ),
    TEEN_1(
        idle = R.drawable.teen_1_idle,
        eating = R.drawable.teen_1_idle,
        sleep = R.drawable.teen_1_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.teen_1_sick,
        play = R.drawable.teen_1_play
    ),
    TEEN_2(
        idle = R.drawable.teen_2_idle,
        eating = R.drawable.teen_2_idle,
        sleep = R.drawable.teen_2_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.teen_2_sick,
        play = R.drawable.teen_2_play
    ),
    ADULT_1(
        idle = R.drawable.adult_1_idle,
        eating = R.drawable.adult_1_idle,
        sleep = R.drawable.adult_1_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_1_sick,
        play = R.drawable.adult_1_play
    ),
    ADULT_2(
        idle = R.drawable.adult_2_idle,
        eating = R.drawable.adult_2_idle,
        sleep = R.drawable.adult_2_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_2_sick,
        play = R.drawable.adult_2_play
    ),
    ADULT_3(
        idle = R.drawable.adult_3_idle,
        eating = R.drawable.adult_3_idle,
        sleep = R.drawable.adult_3_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_3_sick,
        play = R.drawable.adult_3_play
    ),
    ADULT_4(
        idle = R.drawable.adult_4_idle,
        eating = R.drawable.adult_4_idle,
        sleep = R.drawable.adult_4_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_4_sick,
        play = R.drawable.adult_4_play
    ),
    ADULT_5(
        idle = R.drawable.adult_5_idle,
        eating = R.drawable.adult_5_idle,
        sleep = R.drawable.adult_5_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_5_sick,
        play = R.drawable.adult_5_play
    ),
    ADULT_6(
        idle = R.drawable.adult_6_idle,
        eating = R.drawable.adult_6_idle,
        sleep = R.drawable.adult_6_sleep,
        lightsOutSleep = R.drawable.lights_out_sleep,
        lightsOutAwake = R.drawable.lights_out_awake,
        sick = R.drawable.adult_6_sick,
        play = R.drawable.adult_6_play
    ),
    DEAD(
        idle = R.drawable.dead,
        eating = null,
        sleep = null,
        lightsOutSleep = null,
        lightsOutAwake = null,
        sick = null,
        play = R.drawable.dead
    );
}