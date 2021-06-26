package com.theapache64.reky.data.repo

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import com.theapache64.reky.data.local.model.FileNameFormat
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:08
 */
class RecordsRepo @Inject constructor(
    @ApplicationContext val context: Context
) {

    companion object {
        private val fileNameFormats by lazy {
            listOf(
                // OnePlus6
                FileNameFormat(
                    regEx = "^(?<name>.+?)-(?<dateTime>\\d+).\\w+\$".toRegex(),
                    dateTimeFormat = "yyyyMMddHHmmss"
                ),
                FileNameFormat(
                    regEx = "^Call@(?<name>.+?)\\(.+?_(?<dateTime>\\d+).\\w+\$".toRegex(),
                    dateTimeFormat = "yyyyMMddHHmmss"
                ),
                // TODO: Add more file formats to support more device
            )
        }
    }

    suspend fun getRecords(fileNameFormat: FileNameFormat, recordsDir: String): List<File> =
        withContext(Dispatchers.IO) {
            File(recordsDir).listFiles()!!.sortedByDescending {
                fileNameFormat.parse(it.name)!!.dateTime
            }
        }


    suspend fun getDurationInMillis(audioFile: File): Long = withContext(Dispatchers.IO) {
        val uri = Uri.parse(audioFile.absolutePath)
        val mmr = MediaMetadataRetriever()
        mmr.setDataSource(context, uri)
        val durationStr = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
        durationStr?.toLong() ?: -1
    }

    suspend fun findFileNameFormat(recordsDir: String): FileNameFormat? =
        withContext(Dispatchers.IO) {
            val allFileNames = File(recordsDir).listFiles()!!.map { it.name }
            var format: FileNameFormat? = null
            for (fileName in allFileNames) {
                for (fileNameFormat in fileNameFormats) {
                    if (fileName.matches(fileNameFormat.regEx)) {
                        format = fileNameFormat
                        break
                    }
                }

                if (format != null) {
                    break
                }
            }
            Timber.d("Format is: $format");
            format
        }
}