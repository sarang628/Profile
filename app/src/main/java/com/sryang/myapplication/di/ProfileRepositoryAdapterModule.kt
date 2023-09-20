package com.sryang.myapplication.di

import com.sryang.library.entity.user.UserProfile
import com.sryang.torang_repository.repository.profile.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProfileRepositoryAdapterModule {
    @Provides
    fun provideProfileAdapterRepository(
        profileRepository: ProfileRepositoryImpl
    ): com.sarang.profile.viewmodel.ProfileRepository {
        return object : com.sarang.profile.viewmodel.ProfileRepository {
            override suspend fun loadProfile(i: Int): UserProfile {
                return profileRepository.loadProfile(i)
            }
        }
    }
}