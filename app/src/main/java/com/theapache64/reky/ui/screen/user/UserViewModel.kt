package com.theapache64.reky.ui.screen.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.data.local.model.FileNameFormat
import com.theapache64.reky.data.local.model.Recording
import com.theapache64.reky.data.repo.ConfigRepo
import com.theapache64.reky.data.repo.RecordsRepo
import com.theapache64.reky.util.Resource
import com.theapache64.reky.util.TimeUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:20
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    recordsRepo: RecordsRepo,
    configRepo: ConfigRepo
) : ViewModel() {
    companion object {
        const val KEY_USER_NAME = "userName"
        const val KEY_USER_MOBILE = "userMobile"
    }

    private val _pageTitle = MutableStateFlow("")
    val pageTitle = _pageTitle.asStateFlow()

    private val _recordings = MutableStateFlow<Resource<List<Recording>>>(Resource.Idle())
    val recordings = _recordings.asStateFlow()

    init {
        val userName = savedStateHandle.get<String>(KEY_USER_NAME)!!
        val userMobile = savedStateHandle.get<String>(KEY_USER_MOBILE)!!

        _pageTitle.value = userName
        viewModelScope.launch {
            _recordings.value = Resource.Loading()

            val fileNameFormat = configRepo.getFileNameFormat()!!
            val inputDateTimeFormat =
                SimpleDateFormat(fileNameFormat.dateTimeFormat, Locale.getDefault())

            val searchName = userName.replace(" ", "") // remove space
            val searchMobile = userMobile.let { mobile ->
                if (mobile.length > 10) {
                    mobile.substring(mobile.length - 10, mobile.length)
                } else {
                    mobile
                }
            }

            val recordings: List<Recording> = recordsRepo
                .getRecords(
                    fileNameFormat = configRepo.getFileNameFormat()!!,
                    recordsDir = configRepo.getConfig()!!.recordsDir
                )
                .filter { it.name.contains(searchName) || it.name.contains(searchMobile) }
                .map { file ->
                    val duration = millisToDuration(recordsRepo.getDurationInMillis(file))
                    val recordedAt = parseRecordedAt(inputDateTimeFormat, fileNameFormat, file.name)
                    Recording(duration = duration, recordedAt = recordedAt, file = file)
                }

            _recordings.value = Resource.Success(recordings)
        }
    }


    private fun parseRecordedAt(
        inputDateTimeFormat: SimpleDateFormat,
        fileNameFormat: FileNameFormat,
        fileName: String
    ): String {
        val data = fileNameFormat.parse(fileName)!!
        val timestamp = data.dateTime
        val recordedAt = inputDateTimeFormat.parse(timestamp.toString())?.time ?: 0
        return TimeUtils.getTimeAgo(recordedAt)!!
    }


    private fun millisToDuration(durationInMillis: Long): String {
        return when (val seconds = durationInMillis / 1000) {
            in 0..60 -> "${seconds}s"
            in 61..3600 -> {
                val minute = (seconds / 60)
                "${minute}m"
            }
            in 3601..86400 -> {
                val hour = ((seconds / 60) / 60)
                "${hour}h"
            }
            else -> "-"
        }
    }
}