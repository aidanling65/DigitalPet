package com.example.digitalpet.intelligence.nonogram

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NonogramViewModelFactory(private val width: Int, private val height: Int, private val fillProbability: Float) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NonogramViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NonogramViewModel(width,height,fillProbability) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class NonogramViewModel(val width: Int = 10, val height:Int = 10, val fillProbability: Float = 0.5f) : ViewModel() {
    var nonogramGame = NonogramBoard(width,height,fillProbability)

    fun fetchNewNonogram(){
        nonogramGame = NonogramBoard(width,height,fillProbability)
    }
}