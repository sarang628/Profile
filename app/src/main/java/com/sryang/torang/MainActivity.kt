package com.sryang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sryang.myapplication.di.profile_di.ProfileScreen
import com.sryang.torang.compose.follow.FollowScreen
import com.sryang.torang.viewmodel.ProfileViewModel
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.repository.FeedRepository
import com.sryang.torang_repository.repository.LoginRepository
import com.sryang.torang_repository.repository.LoginRepositoryTest
import com.sryang.torang_repository.repository.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiProfile: ApiProfile

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var feedRepository: FeedRepository

    @Inject
    lateinit var loginRepository: LoginRepository

    private val profileViewModel: ProfileViewModel by viewModels()

    val profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/"
    val reviewImageServerUrl = "http://sarang628.iptime.org:89/review_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Column(Modifier.height(700.dp)) {
                            ProfileNavhost(
                                profileImageServerUrl = profileImageServerUrl,
                                reviewImageServerUrl = reviewImageServerUrl,
                                loginRepository = loginRepository
                            )
                        }
                        Column(Modifier.height(300.dp)) {
                            LoginRepositoryTest(loginRepository)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileNavhost(
    profileImageServerUrl: String,
    reviewImageServerUrl: String,
    loginRepository: LoginRepository
) {
    val navHostController = rememberNavController()
    NavHost(navController = navHostController, startDestination = "main") {
        composable("main") {
            Column {
                Button(onClick = { navHostController.navigate("profile/3") }) {
                    Text(text = "profile")
                }
                Button(onClick = { navHostController.navigate("myProfile") }) {
                    Text(text = "myProfile")
                }
                Button(onClick = { navHostController.navigate("follow") }) {
                    Text(text = "follow")
                }
            }
        }
        composable("profile/{id}") {
            ProfileScreen(
                profileImageUrl = profileImageServerUrl,
                imageServerUrl = reviewImageServerUrl,
                navBackStackEntry = it,
                onSetting = {}
            )
        }
        composable("myProfile") {
            ProfileScreen(
                profileImageUrl = profileImageServerUrl,
                imageServerUrl = reviewImageServerUrl,
                navBackStackEntry = null,
                onSetting = {}
            )
        }
        composable("follow") {
            FollowScreen(
                onBack = { navHostController.popBackStack() },
                profileServerUrl = profileImageServerUrl
            )
        }
    }
}