package com.theapache64.reky.ui.screen.config

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.R
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.RecordsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by theapache64 : May 30 Sun,2021 @ 16:45
 */
@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepo: ConfigRepo,
    private val recordsRepo: RecordsRepo
) : ViewModel() {

    lateinit var recordsDir: String

    private val _isConfigFinished = MutableStateFlow<Boolean>(false)
    val isConfigFinished = _isConfigFinished.asStateFlow()

    private val _validationError = MutableStateFlow<Int?>(null)
    val validationError = _validationError.asStateFlow()

    private val _isUnsupportedDevice = MutableStateFlow(false)
    val isUnsupportedDevice = _isUnsupportedDevice.asStateFlow()

    fun onFinishClicked() {
        viewModelScope.launch {
            if (recordsDir.isEmpty() || !recordsDir.startsWith("/")) {
                _validationError.value = R.string.config_error_empty_dir
            } else {

                // Save config
                configRepo.saveRecordsPath(recordsDir)

                // Identify file name format
                val fileNameFormat = recordsRepo.findFileNameFormat(recordsDir)

                if (fileNameFormat != null) {

                    // Save file name format
                    configRepo.saveFileNameFormat(fileNameFormat)

                    _validationError.value = null
                    _isConfigFinished.value = true
                } else {
                    _isUnsupportedDevice.value = true
                }
            }
        }
    }
}