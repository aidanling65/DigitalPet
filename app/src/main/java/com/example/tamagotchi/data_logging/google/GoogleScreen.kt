package com.example.tamagotchi.data_logging.google

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.example.tamagotchi.data_logging.DebugTools

@Composable
fun GoogleScreen(
    viewModel: GoogleViewModel,
    onStart: (InteractionType) -> Unit,
    onFinish: (LoadingState) -> Unit,
) {
    val context = LocalContext.current
    val debugTools = DebugTools(context)
    var showLoggedIn by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        viewModel.restartRequired.collect { needsRestart ->
            if (needsRestart) {
                onFinish(LoadingState.SUCCESS)
            }
        }
    }

    Column {
        GoogleSignInField({onStart(InteractionType.LOGIN)}) {
            if(it.idToken != null) {
                onFinish(LoadingState.SUCCESS)
                showLoggedIn = true
            }
            viewModel.onGoogleSignInSuccess(it)
        }
        AnimatedVisibility(
            showLoggedIn,
            enter = expandVertically {
                -it
            },
            exit = shrinkVertically { -it }
        ) {
            Column {
                Button(onClick = {
                    onStart(InteractionType.BACKUP)
                    viewModel.backup { onFinish(it) }
                }) {
                    Text("Backup Data")
                }
                Button(onClick = {
                    onStart(InteractionType.RESTORE)
                    viewModel.restore()
                }) {
                    Text("Restore Data")
                }
            }
        }
        Button(onClick = { debugTools.exportDataForSharing() }) {
            Text("Export Data")
        }
    }
}
