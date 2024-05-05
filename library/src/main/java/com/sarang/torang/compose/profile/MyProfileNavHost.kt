package com.sarang.torang.compose.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.viewmodel.MyProfileViewModel
import com.sarang.torang.viewmodel.ProfileViewModel

@Composable
fun MyProfileNavHost(
    profileViewModel: MyProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    galleryScreen: @Composable (onNext: (List<String>) -> Unit, onClose: () -> Unit) -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onProfile: ((Int) -> Unit)? = null
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {
        composable("editProfile") {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                onBack = { navController.popBackStack() },
                onEditImage = {
                    navController.navigate("EditProfileImage")
                }
            )
        }
        composable("profile") {
            MyProfileScreen(
                onEditProfile = { navController.navigate("editProfile") },
                onSetting = onSetting,
                profileViewModel = profileViewModel,
                favorite = { favorite.invoke() },
                wantToGo = { wantToGo.invoke() },
                onFollowing = { navController.navigate("myFollow") },
                onWrite = { },
                onFollwer = { navController.navigate("myFollow") },
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin
            )
        }
        composable("EditProfileImage") {
            galleryScreen.invoke(onNext = {
                profileViewModel.updateProfileImage(it[0])
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
    }
}