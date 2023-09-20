package com.sarang.profile.viewmodel

import com.sryang.library.entity.user.UserProfile

interface ProfileRepository {
    suspend fun loadProfile(i: Int) : UserProfile
}