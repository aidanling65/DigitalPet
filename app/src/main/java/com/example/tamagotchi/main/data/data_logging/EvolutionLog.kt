package com.example.tamagotchi.main.data.data_logging

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tamagotchi.main.data.model.AgeStage
import com.example.tamagotchi.main.data.model.EvolutionAnimations
import java.time.LocalDateTime

@Entity(tableName = "evolution_log")
data class EvolutionLog(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "evolution_time")
    var evolveTime: String = LocalDateTime.now().toString(),

    @ColumnInfo(name = "age_stage")
    var ageStage: AgeStage = AgeStage.EGG,

    @ColumnInfo(name = "evolution_type")
    var evolutionType: EvolutionAnimations = EvolutionAnimations.EGG
)