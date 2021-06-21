package com.theapache64.reky.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.theapache64.reky.data.local.model.Config
import com.theapache64.reky.data.local.model.FileNameFormat
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by theapache64 : May 30 Sun,2021 @ 16:46
 */
class ConfigRepo @Inject constructor(
    private val configStore: DataStore<Preferences>
) {
    private val recordsDirKey by lazy {
        stringPreferencesKey(KEY_RECORDS_DIR)
    }

    private val fileNameFormatKey by lazy {
        stringPreferencesKey(KEY_FILE_NAME_FORMAT_REGEX)
    }

    private val dateTimeFormatKey by lazy {
        stringPreferencesKey(KEY_DATE_TIME_FORMAT)
    }

    companion object {
        private const val KEY_RECORDS_DIR = "records_dir"
        private const val KEY_FILE_NAME_FORMAT_REGEX = "file_name_format_regex"
        private const val KEY_DATE_TIME_FORMAT = "date_time_format"
    }

    suspend fun getConfig(): Config? {
        val recordsDir = configStore.data
            .map { it[recordsDirKey] }.first() ?: return null

        return Config(recordsDir)
    }

    suspend fun saveRecordsPath(directory: String) {
        configStore.edit {
            it[recordsDirKey] = directory
        }
    }

    suspend fun saveFileNameFormat(fileNameFormat: FileNameFormat) {
        configStore.edit {
            it[fileNameFormatKey] = fileNameFormat.regEx.toString()
            it[dateTimeFormatKey] = fileNameFormat.dateTimeFormat
        }
    }

    suspend fun getFileNameFormat(): FileNameFormat? {
        val fileNameFormatRegexString = configStore.data.map { it[fileNameFormatKey] }.first()
        val dateTimeFormat = configStore.data.map { it[dateTimeFormatKey] }.first()
        return if (fileNameFormatRegexString != null && dateTimeFormat != null) {
            FileNameFormat(fileNameFormatRegexString.toRegex(), dateTimeFormat)
        } else {
            null
        }
    }
}