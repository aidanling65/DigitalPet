package com.example.tamagotchi.data_logging.google

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.FileContent
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.DriveScopes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class DriveService(context: Context, googleAccount: GoogleSignInAccount) {
    private val drive: Drive

    init {
        val credential =
            GoogleAccountCredential.usingOAuth2(context, listOf(DriveScopes.DRIVE_APPDATA))
        credential.selectedAccount = googleAccount.account
        drive = Drive.Builder(NetHttpTransport(), GsonFactory(), credential)
            .setApplicationName("Tamagotchi")
            .build()
    }

    suspend fun uploadFile(localFile: File, remoteFileName: String) {
        withContext(Dispatchers.IO) {
            try {
                val fileMetadata = com.google.api.services.drive.model.File().apply {
                    name = remoteFileName
                    parents = listOf("appDataFolder")
                }
                val mediaContent = FileContent("application/octet-stream", localFile)

                val fileList = drive.files().list()
                    .setSpaces("appDataFolder")
                    .setQ("name='$remoteFileName'")
                    .setFields("files(id)")
                    .execute()

                if (fileList.files.isEmpty()) {
                    drive.files().create(fileMetadata, mediaContent).execute()
                    Log.d("DriveService", "Uploaded new file: $remoteFileName")
                } else {
                    val fileId = fileList.files[0].id
                    drive.files().update(fileId, fileMetadata, mediaContent).execute()
                }
            } catch (e: IOException) {
                Log.e("DriveService", "File upload failed for $remoteFileName", e)
            }
        }
    }

    suspend fun downloadFile(remoteFileName: String, destinationFile: File): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val fileList = drive.files().list()
                    .setSpaces("appDataFolder")
                    .setQ("name='$remoteFileName'")
                    .setFields("files(id)")
                    .execute()
                if (fileList.files.isEmpty()) {
                    Log.d("DriveService", "File not found: $remoteFileName")
                    return@withContext false
                }

                val fileId = fileList.files.first().id
                val outputStream = FileOutputStream(destinationFile)
                drive.files().get(fileId).executeMediaAndDownloadTo(outputStream)
                outputStream.flush()
                outputStream.close()
                Log.d(
                    "DriveService",
                    "Downloaded file: $remoteFileName to ${destinationFile.absolutePath}"
                )
                return@withContext true
            } catch (e: IOException) {
                Log.e("DriveService", "File download failed for $remoteFileName", e)
                if (destinationFile.exists()) {
                    destinationFile.delete()
                }
                return@withContext false
            }
        }
    }
}