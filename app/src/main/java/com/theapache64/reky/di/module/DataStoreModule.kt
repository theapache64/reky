package com.theapache64.reky.di.module

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by theapache64 : May 30 Sun,2021 @ 17:43
 */
@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.configDataStore: DataStore<Preferences> by preferencesDataStore(name = "configs")

    @Provides
    fun provideConfigStore(
        @ApplicationContext context: Context
    ) = context.configDataStore
}