package com.example.tamagotchi

enum class EvolutionAnimations(val animations: Animations) {
    EGG(
        Animations(
            idle = listOf(1),
            eating = null,
            sleep = null,
            poop = null,
            sick = null
        )
    ),
    BABY(
        Animations(
            idle=listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    CHILD(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    TEEN_1(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    TEEN_2(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_1(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_2(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_3(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_4(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_5(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT_6(
        Animations(
            idle = listOf(1),
            eating = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    DEAD(
        Animations(
            idle = listOf(1),
            eating = null,
            sleep = null,
            poop = null,
            sick = null
        )
    );

    data class Animations(
        val idle: List<Int>,
        val eating: List<Int>?,
        val sleep: List<Int>?,
        val poop: List<Int>?,
        val sick: List<Int>?
    )
}