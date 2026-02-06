package com.example.tamagotchi.data_logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import java.time.LocalTime

@Entity(tableName = "tamagotchi_history")
data class TamagotchiHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "created_at") var gameOpened: String = LocalTime.now().toString(),

    @ColumnInfo(name = "game_closed") var gameClosed: String = LocalTime.now().toString(),

    @ColumnInfo(name = "resets") var resets: Int = 0,
    @ColumnInfo(name = "manual_used") var manualUsed: Int = 0,
    @ColumnInfo(name = "settings_used") var settingsUsed: Int = 0,

    @ColumnInfo(name = "deaths") var deaths: Int = 0,
    @ColumnInfo(name = "ageStage") var ageStage: AgeStage = AgeStage.EGG,
    @ColumnInfo(name = "evolution") var evolution: EvolutionAnimations = EvolutionAnimations.EGG,
    @ColumnInfo(name= "evolution_count") var timesEvolved: Int = 0,

    @ColumnInfo(name = "age") var age: Int = 0,
    @ColumnInfo(name = "fed") var timesFed: Int = 0,

    @ColumnInfo(name = "poop") var timesPooped: Int = 0,
    @ColumnInfo(name = "cleaned") var timesCleaned: Int = 0,

    @ColumnInfo(name = "sick") var timesSick: Int = 0,
    @ColumnInfo(name = "heal") var timesHealed: Int = 0,

    @ColumnInfo(name = "misbehaved") var timesMisbehaved: Int = 0,
    @ColumnInfo(name = "disciplined") var timesDisciplined: Int = 0,

    @ColumnInfo(name = "slept") var timesSlept: Int = 0,
    @ColumnInfo(name = "light") var timesLightsOut: Int = 0,

    @ColumnInfo(name = "jump_played") var timesJumpPlayed: Int = 0,
    @ColumnInfo(name = "flappy_played") var timesFlappyPlayed: Int = 0,

    @ColumnInfo(name = "sudokus_solved") var sudokusSolved: Int = 0,
    @ColumnInfo(name = "sudokus_failed") var sudokusFailed: Int = 0,

    @ColumnInfo(name = "nonograms_solved") var nonogramsSolved: Int = 0,
    @ColumnInfo(name = "nonograms_failed") var nonogramsFailed: Int = 0,

    @ColumnInfo(name = "step_goal_hit") var stepGoalHit: Int = 0,
    @ColumnInfo(name = "step_goal_missed") var stepGoalMissed: Int = 0,

    @ColumnInfo(name = "mistakes_made") var mistakesMade: Int = 0,

    @ColumnInfo(name = "paused") var pausesUsed: Int = 0,
)