package com.sarang.torang.compose.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.viewmodel.ProfileViewModel

@Composable
fun ProfileNavHost(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    onSetting: () -> Unit,
    id: Int,
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(key1 = id, block = {
        id.let {
            profileViewModel.loadProfile(it)
        }
    })

    NavHost(
        navController = navController,
        startDestination = "profile"
    ) {
        composable("profile") {
            InternalProfileScreen(
                onEditProfile = { navController.navigate("editProfile") },
                onSetting = onSetting,
                profileViewModel = profileViewModel,
                favorite = { favorite.invoke() },
                wantToGo = { wantToGo.invoke() },
                onFollowing = { navController.navigate("follow/${id}") },
                onWrite = { },
                onFollwer = { navController.navigate("follow/${id}") },
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin
            )
        }
        composable("follow/{userId}") {
            val userId = it.arguments?.getString("userId")?.toInt()

            if (userId != null) {
                OtherFollowScreen(
                    onBack = { navController.popBackStack() },
                    userId = userId,
                    onProfile = onProfile
                )
            } else {
                Text(text = "사용자 정보가 없습니다.")
            }
        }
    }
}