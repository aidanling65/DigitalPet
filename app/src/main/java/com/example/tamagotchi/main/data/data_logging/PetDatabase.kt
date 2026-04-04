package com.example.tamagotchi.main.data.data_logging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActiveHistory::class, PassiveHistory::class, EvolutionLog::class, SessionLog::class], version = 4, exportSchema = true)
abstract class PetDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: PetDatabase? = null

        fun getDatabase(context: Context) : PetDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PetDatabase::class.java,
                    "tamagotchi_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}