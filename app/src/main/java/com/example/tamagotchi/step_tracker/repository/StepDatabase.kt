package com.example.tamagotchi.step_tracker.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [StepCount::class], version = 1)
abstract class StepDatabase: RoomDatabase() {
    abstract fun stepsDao(): StepsDao

    companion object {
        @Volatile
        private var INSTANCE: StepDatabase? = null

        fun getDatabase(context: Context): StepDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    StepDatabase::class.java,
                    "step_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}