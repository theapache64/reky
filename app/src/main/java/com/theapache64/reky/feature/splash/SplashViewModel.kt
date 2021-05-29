package com.theapache64.reky.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.theapache64.reky.BuildConfig
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

) : ViewModel() {

    companion object {
        private const val SPLASH_DELAY = 2000L
    }

    val appVersion = "v${BuildConfig.VERSION_NAME}"

    private val _isSplashFinished = MutableStateFlow(false)
    val isSplashFinished = _isSplashFinished.asStateFlow()

    init {
        viewModelScope.launch {
            delay(SPLASH_DELAY)
            _isSplashFinished.value = true
        }
    }
}