package com.example.tamagotchi.main.data.data_logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "gameSession")
data class SessionLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "game_opened") var gameOpened: String = LocalTime.now().toString(),
    @ColumnInfo(name = "game_closed") var gameClosed: String = LocalTime.now().toString()
)