package com.sarang.profile.viewmodel

import com.sryang.library.entity.User

interface ProfileRepository {
    suspend fun loadProfile(i: Int) : User
}