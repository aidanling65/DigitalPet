package com.example.tamagotchi

fun loadAnimations(prefix: String) : List<Int>{
    val animations = mutableListOf<Int>()
    val context = MyApp.instance
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

enum class EvolutionAnimations(
    val idle: List<Int>,
    val eating: List<Int>?,
    val sleep: List<Int>?,
    val lights_out_sleep: List<Int>?,
    val lights_out_awake: List<Int>?,
    val poop: List<Int>?,
    val sick: List<Int>?
) {
    EGG(
        idle = loadAnimations("egg_idle"),
        eating = null,
        sleep = null,
        lights_out_sleep = null,
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = null,
        sick = null,
    ),
    BABY(
        idle = loadAnimations("baby_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("baby_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    CHILD(
        idle = loadAnimations("child_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("child_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    TEEN_1(
        idle = loadAnimations("teen_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_1_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    TEEN_2(
        idle = loadAnimations("teen_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("teen_2_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_1(
        idle = loadAnimations("adult_1_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_1_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_2(
        idle = loadAnimations("adult_2_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = listOf(R.drawable.tamagotchi),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_3(
        idle = loadAnimations("adult_3_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = listOf(R.drawable.tamagotchi),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_4(
        idle = loadAnimations("adult_4_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_4_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_5(
        idle = loadAnimations("adult_5_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_5_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    ADULT_6(
        idle = loadAnimations("adult_6_idle"),
        eating = listOf(R.drawable.tamagotchi),
        sleep = loadAnimations("adult_6_sleep"),
        lights_out_sleep = loadAnimations("lights_out_sleep"),
        lights_out_awake = listOf(R.drawable.lights_out_awake),
        poop = listOf(R.drawable.tamagotchi),
        sick = listOf(R.drawable.tamagotchi)
    ),
    DEAD(
        idle = loadAnimations("dead"),
        eating = null,
        sleep = null,
        lights_out_sleep = null,
        lights_out_awake = null,
        poop = null,
        sick = null
    );
}