package com.sryang.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sarang.profile.ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.repository.profile.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var apiProfile: ApiProfile

    @Inject
    lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            profileViewModel.loadProfile(0)
            ProfileScreen(
                profileBaseUrl = "http://sarang628.iptime.org:89/profile_images/",
                profileViewModel = profileViewModel, onLogout = {

                })
        }
    }
}