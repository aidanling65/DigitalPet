package com.example.tamagotchi

enum class EvolutionAnimations(val animations: Animations) {
    EGG(
        Animations(
            idle = listOf(1),
            sleep = null,
            poop = null,
            sick = null
        )
    ),
    BABY(
        Animations(
            listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    CHILD(
        Animations(
            idle = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    TEEN(
        Animations(
            idle = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    ADULT(
        Animations(
            idle = listOf(1),
            sleep = listOf(1),
            poop = listOf(1),
            sick = listOf(1)
        )
    ),
    DEAD(
        Animations(
            idle = listOf(1),
            sleep = null,
            poop = null,
            sick = null
        )
    );

    data class Animations(
        val idle: List<Int>,
        val sleep: List<Int>?,
        val poop: List<Int>?,
        val sick: List<Int>?
    )
}