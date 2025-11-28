package com.sarang.torang.compose

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.compose.profile.ProfileScreen
import com.sarang.torang.viewmodel.ProfileViewModel

typealias ProfileImageType = @Composable (ProfileImageTypeData) -> Unit

val LocalProfileImage : ProvidableCompositionLocal<ProfileImageType> = compositionLocalOf<ProfileImageType> {
    @Composable {

    }
}

data class ProfileImageTypeData(
    val modifier : Modifier = Modifier,
    val url : String = "",
    val errorIconSize : Dp = 30.dp,
    val progressSize : Dp = 30.dp,
    val contentScale : ContentScale = ContentScale.Fit
)

typealias MyFeedType = @Composable (Int) -> Unit

val LocalMyFeed : ProvidableCompositionLocal<MyFeedType> = compositionLocalOf<MyFeedType> {
    @Composable {

    }
}

@Composable
fun ProfileScreenNavHost(id             : Int? = null,
                         onEmailLogin   : () -> Unit = { },
                         onReview       : (Int) -> Unit = { },
                         navController  : NavHostController = rememberNavController(),
                         onClose        : () -> Unit = { },
                         onMessage      : (Int) -> Unit = { }) {
    val tag = "__ProfileScreenNavHost"
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
                    page = it.arguments?.getString("page")?.toInt()
                )
            } else {
                Text(text = "사용자 정보가 없습니다.")
            }
        }
        composable("myFeed/{reviewId}") {
            it.arguments?.getString("reviewId")?.toInt()?.let {
                LocalMyFeed.current.invoke(it)
            } ?: run {
                Log.e(tag, "reviewId is null : ${it.arguments?.getString("reviewId")}")
            }
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
                onMessage = {}
            )
        }
    }
}