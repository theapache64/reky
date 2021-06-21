package com.theapache64.reky.ui.screen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

/**
 * Created by theapache64 : May 30 Sun,2021 @ 18:24
 */
@HiltViewModel
class MainViewModel @Inject constructor(
) : ViewModel() {

    private val _pickedDirectory = MutableStateFlow<String>("(choose directory)")
    val pickedDirectory = _pickedDirectory.asStateFlow()

    fun onDirPicked(directory: String) {
        _pickedDirectory.value = directory
    }

}