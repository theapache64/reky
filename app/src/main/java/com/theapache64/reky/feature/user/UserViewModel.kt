package com.theapache64.reky.feature.user

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.theapache64.reky.data.local.model.Recording
import com.theapache64.reky.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:20
 */
@HiltViewModel
class UserViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
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
        savedStateHandle.get<String>(KEY_USER_NAME)?.also { userName ->
            _pageTitle.value = userName
        }
    }
}