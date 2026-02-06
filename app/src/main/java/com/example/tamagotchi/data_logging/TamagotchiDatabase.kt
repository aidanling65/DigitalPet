package com.example.tamagotchi.data_logging

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ActiveHistory::class, PassiveHistory::class], version = 1, exportSchema = true)
abstract class TamagotchiDatabase: RoomDatabase() {
    abstract fun historyDao(): HistoryDao

    companion object{
        @Volatile
        private var INSTANCE: TamagotchiDatabase? = null

        fun getDatabase(context: Context) : TamagotchiDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TamagotchiDatabase::class.java,
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