package com.theapache64.reky.ui.screen

import androidx.core.text.isDigitsOnly
import androidx.lifecycle.ViewModel
import com.theapache64.reky.data.local.model.User
import com.theapache64.reky.util.flow.mutableEventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by theapache64 : May 30 Sun,2021 @ 18:24
 */
@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _pickedDirectory = MutableStateFlow<String>("(choose directory)")
    val pickedDirectory = _pickedDirectory.asStateFlow()

    private val _dial = mutableEventFlow<String>()
    val dial = _dial.asSharedFlow()

    fun onDirPicked(directory: String) {
        _pickedDirectory.value = directory
    }

    fun onUserLongClicked(user: User) {
        Timber.d("Clicked $user");
        if (user.contact.name.isDigitsOnly()) {
            _dial.tryEmit(user.contact.name)
        }
    }

}