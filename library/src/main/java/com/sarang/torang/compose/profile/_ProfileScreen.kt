package com.sarang.torang.compose.profile

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.compose.profile.components.InternalProfileScreen
import com.sarang.torang.viewmodel.ProfileViewModel

@Composable
fun _ProfileScreen(
    profileViewModel: ProfileViewModel = hiltViewModel(),
    id: Int? = null,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onClose: (() -> Unit)? = null,
    onEmailLogin: () -> Unit,
    onProfile: ((Int) -> Unit)? = null,
    onReview: ((Int) -> Unit)? = null,
    navController: NavHostController = rememberNavController()
) {
    LaunchedEffect(key1 = id, block = {
        id?.let {
            profileViewModel.loadProfile(it)
        }
    })

    NavHost(
        navController = navController,
        startDestination = if (id == null) "error" else "profileHome"
    ) {
        composable("error") {
            Text(text = "사용자 정보가 없습니다.")
        }
        composable("profileHome") {
            InternalProfileScreen(
                profileViewModel = profileViewModel,
                onFollowing = { navController.navigate("follow/${id}") },
                onWrite = { },
                onFollwer = { navController.navigate("follow/${id}") },
                onClose = { onClose?.invoke() },
                onEmailLogin = onEmailLogin,
                onReview = onReview
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
                onReview = onReview
            )
        }
    }
}