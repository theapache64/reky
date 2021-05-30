package com.theapache64.reky

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by theapache64 : May 29 Sat,2021 @ 17:05
 */
@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}