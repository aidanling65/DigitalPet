package com.example.digitalpet.step_tracker.repository

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "steps")
data class StepCount(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,

    @ColumnInfo(name="steps") val steps: Long,

    @ColumnInfo(name="created_at") val createdAt: String,
)