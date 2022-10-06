package com.sryang.myapplication.di

import com.sryang.torang_core.data.LocationPreferences
import com.sryang.torang_repository.repository.impl.LocationPreferencesImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class PreferenceModule() {
    @Binds
    abstract fun provideLocationPreferences(locationPreferences: LocationPreferencesImpl): LocationPreferences
//    abstract fun provideLocationPreferences(locationPreferences: TestLocationPreferencesImpl): LocationPreferences
}