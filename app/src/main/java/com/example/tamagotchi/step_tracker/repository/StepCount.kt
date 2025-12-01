package com.example.tamagotchi.step_tracker.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
data class StepCount(
    @PrimaryKey
    @ColumnInfo(name="steps") val steps: Long,

    @ColumnInfo(name="created_at") val createdAt: String,
)