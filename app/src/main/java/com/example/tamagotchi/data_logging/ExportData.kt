package com.example.tamagotchi.data_logging

import android.content.Context
import android.content.Intent
import com.example.tamagotchi.BuildConfig
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

class DebugTools(private val context: Context) {
    companion object {
        const val DB_NAME = "tamagotchi_database"
        const val PREFS_NAME = "tamagotchi_prefs.preferences_pb"
    }

    fun exportDataForSharing() {
        try {
            val cacheDir = context.cacheDir
            val exportDir = File(cacheDir, "exports")
            if (!exportDir.exists()) {
                exportDir.mkdirs()
            }

            val zipFile = File(exportDir, "tamagotchi_backup.zip")
            if (zipFile.exists()) {
                zipFile.delete()
            }

            val filesToZip = mutableListOf<File>()

            val dbFile = context.getDatabasePath(DB_NAME)
            if (dbFile.exists()) {
                filesToZip.add(dbFile)

                val dbWal = File(dbFile.path + "-wal")
                if (dbWal.exists()) {
                    filesToZip.add(dbWal)
                }
                val dbShm = File(dbFile.path + "-shm")
                if (dbShm.exists()) filesToZip.add(dbShm)
            } else {
                Log.w("DebugTools", "Database file not found at ${dbFile.path}")
            }

            val prefsDir = File(context.filesDir, "datastore")
            val prefsFile = File(prefsDir, PREFS_NAME)
            if (prefsFile.exists()) {
                filesToZip.add(prefsFile)
            } else {
                Log.w("DebugTools", "Preferences file not found at ${prefsFile.path}")
            }

            ZipOutputStream(FileOutputStream(zipFile)).use { zipOut ->
                for (file in filesToZip) {
                    FileInputStream(file).use { fileIn ->
                        val zipEntry = ZipEntry(file.name)
                        zipOut.putNextEntry(zipEntry)
                        fileIn.copyTo(zipOut)
                    }
                }
            }
            shareFile(zipFile)
        } catch(e: Exception) {
            Log.e("DebugTools", "Failed to export data", e)
        }
    }

    private fun shareFile(file: File) {
        val authority = "${BuildConfig.APPLICATION_ID}.provider"
        val contentUri: Uri = FileProvider.getUriForFile(context, authority, file)

        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_STREAM, contentUri)
            type = "application/zip"
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(shareIntent, "Export Tamagotchi Data")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }
}