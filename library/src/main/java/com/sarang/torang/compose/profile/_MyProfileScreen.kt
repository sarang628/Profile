package com.sarang.torang.compose.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.ProfileUiState
import com.sarang.torang.compose.FeedListScreen
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.profile.components.InternalProfileScreen
import com.sarang.torang.compose.profile.components.InternalMyProfileScreen
import com.sarang.torang.viewmodel.MyProfileViewModel
import com.sarang.torang.viewmodel.ProfileViewModel

@Composable
fun _MyProfileScreen(
    myProfileViewModel: MyProfileViewModel = hiltViewModel(),
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    galleryScreen: @Composable (onNext: (List<String>) -> Unit, onClose: () -> Unit) -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    onReview: ((Int) -> Unit)? = null,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "myProfile"
    ) {
        composable("editProfile") {
            EditProfileScreen(
                profileViewModel = myProfileViewModel,
                onBack = { navController.popBackStack() },
                onEditImage = {
                    navController.navigate("EditProfileImage")
                }
            )
        }
        composable("myProfile") {
            InternalMyProfileScreen(
                onEditProfile = { navController.navigate("editProfile") },
                onSetting = onSetting,
                profileViewModel = myProfileViewModel,
                onFollowing = { navController.navigate("myFollow") },
                onWrite = { },
                onFollwer = { navController.navigate("myFollow") },
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin,
                onReview = onReview
            )
        }
        composable("EditProfileImage") {
            galleryScreen.invoke(onNext = {
                myProfileViewModel.updateProfileImage(it[0])
                navController.popBackStack()
            }, onClose = {
                navController.popBackStack()
            })
        }

        composable("myFollow") {
            MyFollowScreen(
                onBack = { navController.popBackStack() },
                onProfile = onProfile
            )
        }
        composable("myFeed/{reviewId}") {
            myFeed.invoke(it)
        }
        composable("profile/{userId}") {
            InternalProfileScreen(
                profileViewModel = profileViewModel,
                onFollowing = { navController.navigate("follow/${id}") },
                onWrite = { },
                onFollwer = { navController.navigate("follow/${id}") },
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin,
                onReview = {}
            )
        }
    }
}