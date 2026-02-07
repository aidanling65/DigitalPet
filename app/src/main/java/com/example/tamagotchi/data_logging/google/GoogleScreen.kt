package com.example.tamagotchi.data_logging.google

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun GoogleScreen(viewModel: GoogleViewModel){
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.restartRequired.collect { needsRestart ->
            if(needsRestart){
                showRestartDialog(context)
            }
        }
    }

    Column{
        GoogleSignInField(){
            viewModel.onGoogleSignInSuccess(it)
        }

        Button(onClick={viewModel.backup()}){
            Text("Backup Data")
        }
        Button(onClick={viewModel.restore()}){
            Text("Restore Data")
        }
    }
}


private fun showRestartDialog(context: Context) {
    AlertDialog.Builder(context)
        .setTitle("Restore Complete")
        .setMessage("Your data has been restored. Please restart the app for the changes to take effect.")
        .setPositiveButton("Exit") { _, _ ->
            (context as? Activity)?.finishAffinity()
        }
        .setCancelable(false)
        .show()
}
