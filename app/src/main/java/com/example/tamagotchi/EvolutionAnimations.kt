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
    val idle: List<Int>?,
    val eating: List<Int>?,
    val sleep: List<Int>?,
    val poop: List<Int>?,
    val sick: List<Int>?
) {
    EGG(
        idle = loadAnimations("egg_idle"),
        eating = null,
        sleep = null,
        poop = null,
        sick = null
    ),
    BABY(
        idle = loadAnimations("baby_idle"),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    CHILD(
        idle = loadAnimations("child_idle"),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    TEEN_1(
        idle = loadAnimations("teen_1_idle"),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    TEEN_2(
        idle = loadAnimations("teen_2_idle"),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_1(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_2(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_3(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_4(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_5(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_6(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    DEAD(
        idle = listOf(R.drawable.tamagotchi, R.drawable.tamagotchi),
        eating = null,
        sleep = null,
        poop = null,
        sick = null
    );
}