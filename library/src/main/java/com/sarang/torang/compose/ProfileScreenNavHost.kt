package com.sarang.torang.compose

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.compose.profile.ProfileScreen
import com.sarang.torang.viewmodel.ProfileViewModel

@Composable
fun ProfileScreenNavHost(
    id: Int? = null,
    myFeed: @Composable (NavBackStackEntry) -> Unit,
    onEmailLogin: () -> Unit,
    onReview: ((Int) -> Unit)? = null,
    navController: NavHostController = rememberNavController(),
    onClose: () -> Unit,
    onMessage: (Int) -> Unit,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit,
) {
    NavHost(
        navController = navController,
        startDestination = "profileHome"
    ) {
        composable("profileHome") {
            ProfileScreen(
                onFollowing = { navController.navigate("follow/${id}/1") },
                onWrite = { },
                onFollwer = { navController.navigate("follow/${id}/0") },
                onClose = { onClose.invoke() },
                onEmailLogin = onEmailLogin,
                id = id,
                onReview = onReview,
                image = image,
                onMessage = {
                    id?.let { onMessage.invoke(it) }

                    if (id == null) {
                        Log.e("__ProfileScreenNavHost", "Message click but id is null!")
                    }
                }
            )
        }
        composable("follow/{userId}/{page}") {
            val userId = it.arguments?.getString("userId")?.toInt()

            if (userId != null) {
                OtherFollowScreen(
                    onBack = { navController.popBackStack() },
                    userId = userId,
                    onProfile = { navController.navigate("profile/${it}") },
                    page = it.arguments?.getString("page")?.toInt(),
                    image = image
                )
            } else {
                Text(text = "사용자 정보가 없습니다.")
            }
        }
        composable("myFeed/{reviewId}") {
            myFeed.invoke(it)
        }
        composable("profile/{userId}") {
            ProfileScreen(
                onFollowing = {
                    navController.navigate(
                        "follow/${
                            it.arguments?.getString("userId")?.toInt()
                        }/1"
                    )
                },
                onWrite = { },
                onFollwer = {
                    navController.navigate(
                        "follow/${
                            it.arguments?.getString("userId")?.toInt()
                        }/0"
                    )
                },
                onClose = { navController.popBackStack() },
                onEmailLogin = onEmailLogin,
                onReview = onReview,
                id = it.arguments?.getString("userId")?.toInt(),
                image = image,
                onMessage = {}
            )
        }
    }
}