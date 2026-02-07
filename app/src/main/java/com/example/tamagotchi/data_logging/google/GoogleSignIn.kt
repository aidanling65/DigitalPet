package com.example.tamagotchi.data_logging.google

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope

@Composable
fun GoogleSignInField(onSignInSuccess: (GoogleSignInAccount) -> Unit) {
    val context = LocalContext.current
    val googleSignInClient = remember {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("856779955997-ra0d2hguj3ol1s9015h9vpp3lu6u1kdv.apps.googleusercontent.com")
            .requestScopes(Scope("https://www.googleapis.com/auth/drive.appdata"))
            .requestEmail()
            .build()
        GoogleSignIn.getClient(context, gso)
    }

    Log.d("GoogleSignIn", "$googleSignInClient")
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        Log.d("GoogleSignIn", result.toString())
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)!!

            if(account.idToken != null) {
                Log.d("GoogleSignIn", "Success! Account email: ${account.email}")
                onSignInSuccess(account)
            }
            else{
                Log.w("GoogleSignnI", "signInResult:failed, account is null.")
            }
        } catch (e: ApiException) {
            Log.w("GoogleSignIn", "signInResult:failed code=" + e.statusCode)
        }
    }
    Log.d("GoogleSignIn", "$launcher")

    Button(onClick = {
        launcher.launch(googleSignInClient.signInIntent)
    }) {
        Text("Sign in with Google")
    }
}