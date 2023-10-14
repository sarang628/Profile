package com.sryang.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Button
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.instagralleryModule.gallery.GalleryScreen
import com.sarang.profile.edit.EditProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.myapplication.di.profile.ProfileScreen
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.repository.FeedRepository
import com.sryang.torang_repository.repository.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var apiProfile: ApiProfile

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var feedRepository: FeedRepository

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
                    val id = it.arguments?.getString("id")?.toInt()
                    profileViewModel.loadProfile(id!!)
                    ProfileScreen(
                        profileViewModel = profileViewModel,
                        profileImageUrl = "http://sarang628.iptime.org:89/profile_images/",
                        imageServerUrl = "http://sarang628.iptime.org:89/review_images/",
                        onEditProfile = {
                            navHostController.navigate("profileEdit")
                        }
                    )
                }
                composable("profileEdit") {
                    EditProfileScreen(
                        profileImageServerUrl = "http://sarang628.iptime.org:89/profile_images/",
                        profileViewModel = profileViewModel,
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
                        profileViewModel.updateProfileImage(1, it[0])
                    }, onClose = {})
                }
            }

            //FeedRepositoryTest(feedRepository = feedRepository)
        }
    }
}