package com.example.tamagotchi.main.data.data_logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "tamagotchi_passive_history")
data class PassiveHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name="created_at") var string: String = LocalTime.now().toString(),

    @ColumnInfo(name = "deaths") var deaths: Int = 0,
    @ColumnInfo(name = "evolution_count") var timesEvolved: Int = 0,

    @ColumnInfo(name = "age") var age: Int = 0,
    @ColumnInfo(name = "poop") var timesPooped: Int = 0,
    @ColumnInfo(name = "sick") var timesSick: Int = 0,
    @ColumnInfo(name = "misbehaved") var timesMisbehaved: Int = 0,
    @ColumnInfo(name = "slept") var timesSlept: Int = 0,

    @ColumnInfo(name = "step_goal_hit") var stepGoalHit: Int = 0,
    @ColumnInfo(name = "step_goal_missed") var stepGoalMissed: Int = 0,

    @ColumnInfo(name = "mistakes_made") var mistakesMade: Int = 0,
)
