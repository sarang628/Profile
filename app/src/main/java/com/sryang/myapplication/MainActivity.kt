package com.sryang.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.myapplication.di.profile.ProfileScreen
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
            val navHostController = rememberNavController()
            NavHost(navController = navHostController, startDestination = "main") {
                composable("main") {
                    Button(onClick = { navHostController.navigate("profile/1") }) {

                    }
                }
                composable("profile/{id}") {
                    ProfileScreen(profileViewModel, it)
                }
            }
        }
    }
}