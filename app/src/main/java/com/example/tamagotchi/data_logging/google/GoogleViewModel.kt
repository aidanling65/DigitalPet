package com.example.tamagotchi.data_logging.google

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.tamagotchi.data_logging.TamagotchiDatabase
import com.example.tamagotchi.main.data.repository.dataStore
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class GoogleViewModel(application: Application) : AndroidViewModel(application) {
    private var backupManager: BackupManager? = null

    fun onGoogleSignInSuccess(account: GoogleSignInAccount) {
        val context = getApplication<Application>().applicationContext
        val driveService = DriveService(context, account)
        val database = TamagotchiDatabase.getDatabase(context)
        val dataStore = context.dataStore

        backupManager = BackupManager(context, driveService, database, dataStore)

    }

    fun backup(onFinish:(LoadingState) -> Unit){
        viewModelScope.launch {
            backupManager?.backupData(onFinish)
        }
    }

    private val _restartRequired = MutableSharedFlow<Boolean>()
    val restartRequired: SharedFlow<Boolean> = _restartRequired

    fun restore(){
        viewModelScope.launch {
            val needsRestart = backupManager?.restoreData() ?: false
            if(needsRestart){
                _restartRequired.emit(true)
            }
        }
    }

}