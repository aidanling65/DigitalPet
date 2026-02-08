package com.example.tamagotchi.main.data.data_logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tamagotchi_active_history",
    foreignKeys = [
        ForeignKey(
            entity= SessionLog::class,
            parentColumns = ["id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        )
    ])
data class ActiveHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name="session_id", index=true) var sessionId: Int = 0,

    @ColumnInfo(name = "resets") var resets: Int = 0,
    @ColumnInfo(name = "manual_used") var manualUsed: Int = 0,
    @ColumnInfo(name = "settings_used") var settingsUsed: Int = 0,

    @ColumnInfo(name = "fed") var timesFed: Int = 0,

    @ColumnInfo(name = "cleaned") var timesCleaned: Int = 0,
    @ColumnInfo(name = "heal") var timesHealed: Int = 0,
    @ColumnInfo(name = "disciplined") var timesDisciplined: Int = 0,
    @ColumnInfo(name = "light") var timesLightsOut: Int = 0,

    @ColumnInfo(name = "jump_played") var timesJumpPlayed: Int = 0,
    @ColumnInfo(name = "flappy_played") var timesFlappyPlayed: Int = 0,

    @ColumnInfo(name = "sudokus_solved") var sudokusSolved: Int = 0,
    @ColumnInfo(name = "sudokus_failed") var sudokusFailed: Int = 0,

    @ColumnInfo(name = "nonograms_solved") var nonogramsSolved: Int = 0,
    @ColumnInfo(name = "nonograms_failed") var nonogramsFailed: Int = 0,

    @ColumnInfo(name = "paused") var pausesUsed: Int = 0,
)