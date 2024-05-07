package com.sarang.torang.compose

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.profile.MyProfileScreen
import com.sarang.torang.viewmodel.MyProfileViewModel

@Composable
fun _MyProfileScreenNavHost(
    myProfileViewModel: MyProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    galleryScreen: @Composable (onNext: (List<String>) -> Unit, onClose: () -> Unit) -> Unit,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
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
            MyProfileScreen(
                profileViewModel = myProfileViewModel,
                onEditProfile = { navController.navigate("editProfile") },
                onSetting = onSetting,
                onFollowing = { navController.navigate("myFollow/1") },
                onWrite = { },
                onFollwer = { navController.navigate("myFollow/0") },
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

        composable("myFollow/{page}") {
            MyFollowScreen(
                onBack = { navController.popBackStack() },
                onProfile = { navController.navigate("profile/${it}") },
                page = it.arguments?.getString("page")?.toInt()
            )
        }
        composable("myFeed/{reviewId}") {
            myFeed.invoke(it)
        }
        composable("profile/{userId}") {
            ProfileScreenNavHost(
                id = it.arguments?.getString("userId")?.toInt(),
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin,
                onReview = onReview,
                myFeed = myFeed
            )
        }
    }
}