package com.theapache64.reky.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.BuildConfig
import com.theapache64.reky.data.repo.ConfigRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:18
 */
@HiltViewModel
class SplashViewModel @Inject constructor(
    private val configRepo: ConfigRepo
) : ViewModel() {

    companion object {
        private val SPLASH_DELAY = if (BuildConfig.DEBUG) {
            0
        } else {
            2000L
        }
    }

    val appVersion = "v${BuildConfig.VERSION_NAME}"

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished = _isSplashFinished.asStateFlow()

    var isConfigSet: Boolean = false

    init {
        viewModelScope.launch {
            isConfigSet = configRepo.getConfig() != null // Checking if config is set or not
            delay(SPLASH_DELAY)
            _isSplashFinished.value = true
        }
    }
}