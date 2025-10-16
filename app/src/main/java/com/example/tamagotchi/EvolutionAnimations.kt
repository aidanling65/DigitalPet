package com.example.tamagotchi

enum class EvolutionAnimations(
    val idle: List<Int>?,
    val eating: List<Int>?,
    val sleep: List<Int>?,
    val poop: List<Int>?,
    val sick: List<Int>?
) {
    EGG(
        idle = listOf(1),
        eating = null,
        sleep = null,
        poop = null,
        sick = null
    ),
    BABY(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    CHILD(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    TEEN_1(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    TEEN_2(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_1(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_2(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_3(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_4(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_5(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    ADULT_6(
        idle = listOf(1),
        eating = listOf(1),
        sleep = listOf(1),
        poop = listOf(1),
        sick = listOf(1)
    ),
    DEAD(
        idle = listOf(1),
        eating = null,
        sleep = null,
        poop = null,
        sick = null
    );
}