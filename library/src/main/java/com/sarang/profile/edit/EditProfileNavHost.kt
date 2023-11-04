package com.sarang.profile.edit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.profile._ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel

@Composable
fun ProfileNavHost(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    profileImageServerUrl: String,
    isMyProfile: Boolean,
    onSetting : ()->Unit,
    id: Int?,
    galleryScreen: @Composable (onNext: (List<String>) -> Unit, onClose: () -> Unit) -> Unit,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
) {
    val navController = rememberNavController()

    LaunchedEffect(key1 = isMyProfile, block = {
        if (isMyProfile) {
            profileViewModel.loadProfileByToken()
        } else {
            id?.let {
                profileViewModel.loadProfile(it)
            }
        }
    })

    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {
        composable("editProfile") {
            EditProfileScreen(
                profileViewModel = profileViewModel,
                profileImageServerUrl = profileImageServerUrl,
                onBack = { navController.popBackStack() },
                onEditImage = {
                    navController.navigate("EditProfileImage")
                }
            )
        }
        composable("profile") {
            _ProfileScreen(
                isMyProfile = isMyProfile,
                profileImageUrl = profileImageServerUrl,
                onEditProfile = { navController.navigate("editProfile") },
                onSetting = onSetting,
                profileViewModel = profileViewModel,
                favorite = { favorite.invoke() },
                wantToGo = { wantToGo.invoke() }
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
    }
}