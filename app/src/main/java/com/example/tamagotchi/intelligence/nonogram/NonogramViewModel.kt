package com.example.tamagotchi.intelligence.nonogram

import androidx.lifecycle.ViewModel

class NonogramViewModel : ViewModel() {
    var nonogramGame = NonogramBoard(10,10,0.5f)

    fun fetchNewNonogram(){
        nonogramGame = NonogramBoard(10,10,0.5f)
    }
}