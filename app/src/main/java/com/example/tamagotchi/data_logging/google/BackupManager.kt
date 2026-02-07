package com.example.tamagotchi.data_logging.google

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.tamagotchi.data_logging.TamagotchiDatabase
import kotlinx.coroutines.flow.first
import java.io.File

const val DATABASE_REMOTE_NAME = "tamagotchi_database.db"
const val PREFERENCES_REMOTE_NAME = "tamagotchi_prefs.preferences_pb"

class BackupManager(
    private val context: Context,
    private val driveService: DriveService,
    private val database: TamagotchiDatabase,
    private val dataStore: DataStore<Preferences>
) {
    suspend fun backupData(){
        Log.d("BackupManager", "Starting data backup...")

        val dbFile = context.getDatabasePath("tamagotchi_database")

        if(database.isOpen){
            database.close()
        }
        if(dbFile.exists()){
            driveService.uploadFile(dbFile, DATABASE_REMOTE_NAME)

            TamagotchiDatabase.Companion.getDatabase(context)
        } else{
            Log.w("BackupManager", "Database file not found for backup.")
        }

        dataStore.data.first()
        val prefsFile = File(context.filesDir, "datastore/$PREFERENCES_REMOTE_NAME")
        if(prefsFile.exists()){
            driveService.uploadFile(prefsFile, PREFERENCES_REMOTE_NAME)
        } else{
            Log.w("BackupManager", "Preferences file not found for backup.")

        }
    }

    suspend fun restoreData(): Boolean{
        Log.d("BackupManager", "Starting data restore...")

        if(database.isOpen){
            database.close()
        }

        val dbFile = context.getDatabasePath("tamagotchi_database")
        val dbSuccess = driveService.downloadFile(DATABASE_REMOTE_NAME, dbFile)

        val prefsFile= File(context.filesDir, "datastore/$PREFERENCES_REMOTE_NAME")
        if(!prefsFile.parentFile.exists()){
            prefsFile.parentFile.mkdirs()
        }

        val prefSuccess = driveService.downloadFile(PREFERENCES_REMOTE_NAME, prefsFile)

        if(dbSuccess || prefSuccess){
            Log.d("BackupManager", "Data restored successfully.")
            return true
        }

        Log.w("BackupManager", "Data restore failed or no remote data found.")
        TamagotchiDatabase.Companion.getDatabase(context)
        return false
    }

}