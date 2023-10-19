package com.sryang.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sarang.profile.edit.EditProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.myapplication.di.profile.ProfileScreen
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

    val profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            NavHost(navController = navHostController, startDestination = "main") {
                composable("main") {
                    Column {
                        Button(onClick = { navHostController.navigate("profile/1") }) {
                            Text(text = "profile")
                        }
                        Button(onClick = { navHostController.navigate("myProfile") }) {
                            Text(text = "myProfile")
                        }
                        Button(onClick = { navHostController.navigate("login") }) {
                            Text(text = "login")
                        }
                    }
                }
                composable("profile/{id}") {
                    ProfileScreen(
                        id = it.arguments?.getString("id")?.toInt(),
                        isMyProfile = false,
                        profileImageUrl = profileImageServerUrl,
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                        onEditProfile = {
                            navHostController.navigate("profileEdit")
                        }
                    )
                }
                composable("myProfile") {
                    ProfileScreen(
                        isMyProfile = true,
                        profileImageUrl = profileImageServerUrl,
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                        onEditProfile = {
                            navHostController.navigate("profileEdit")
                        }
                    )
                }
                composable("profileEdit") {
                    EditProfileScreen(
                        profileImageServerUrl = profileImageServerUrl,
                        onBack = {
                            navHostController.popBackStack()
                        },
                        onEditImage = {
                            navHostController.navigate("EditProfileImage")
                        }
                    )
                }
                composable("EditProfileImage") {
                    GalleryScreen(onNext = {
                        //profileViewModel.updateProfileImage(it[0])
                    }, onClose = {})
                }
                composable("login") {
                    LoginRepositoryTest(loginRepository = loginRepository)
                }
            }

            //FeedRepositoryTest(feedRepository = feedRepository)
        }
    }
}