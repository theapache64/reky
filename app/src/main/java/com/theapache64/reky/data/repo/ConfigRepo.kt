package com.theapache64.reky.data.repo

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.theapache64.reky.data.local.model.Config
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

    companion object {
        private const val KEY_RECORDS_DIR = "records_dir"
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
}