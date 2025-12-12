package com.sarang.torang.compose.profile

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.compose.edit.EditProfileScreen
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.viewmodel.profile.MyProfileViewModel

@Composable
fun _MyProfileScreenNavHost(
    myProfileViewModel          : MyProfileViewModel    = hiltViewModel(),
    onSetting                   : () -> Unit            = { },
    onClose                     : () -> Unit            = { },
    onEmailLogin                : () -> Unit            = { },
    onReview                    : (Int) -> Unit         = { },
    onMessage                   : (Int) -> Unit         = { },
    navController               : NavHostController     = rememberNavController(),
    myProfileBackgroundColor    : Color                 = Color.Transparent,
    contentWindowInsets         : WindowInsets          = ScaffoldDefaults.contentWindowInsets,
    galleryScreen               : @Composable (onNext: (List<String>) -> Unit, onClose: () -> Unit) -> Unit = {_,_->}) {
    NavHost(
        navController = navController,
        startDestination = "myProfile"
    ) {
        composable("editProfile") {
            val uiState by myProfileViewModel.uiState.collectAsStateWithLifecycle()
            EditProfileScreen(uiState = uiState,
                              onBack = { navController.popBackStack() },
                              onEditImage = { navController.navigate("EditProfileImage") })
        }
        composable("myProfile") {
            MyProfileScreen(myProfileViewModel  = myProfileViewModel,
                            onEditProfile       = { navController.navigate("editProfile") },
                            onSetting           = onSetting,
                            onFollowing         = { navController.navigate("myFollow/1") },
                            onWrite             = { },
                            onFollower           = { navController.navigate("myFollow/0") },
                            onEmailLogin        = onEmailLogin,
                            onReview            = onReview,
                            backgroundColor     = myProfileBackgroundColor,
                            contentWindowInsets = contentWindowInsets)
        }
        composable("EditProfileImage") {
            galleryScreen.invoke({
                myProfileViewModel.updateProfileImage(it[0])
                navController.popBackStack() },
        { navController.popBackStack() })
        }

        composable("myFollow/{page}") {
            MyFollowScreen(onBack = { navController.popBackStack() },
                           onProfile = { navController.navigate("profile/${it}") },
                           page = it.arguments?.getString("page")?.toInt())
        }
        composable("myFeed/{reviewId}") {
            it.arguments?.getString("reviewId")?.toInt()?.let {
                LocalMyFeed.current.invoke(it)
            }
        }
        composable("profile/{userId}") {
            ProfileScreenNavHost(
                id = it.arguments?.getString("userId")?.toInt(),
                onClose = { onClose.invoke() },
                onEmailLogin = onEmailLogin,
                onReview = onReview,
                onMessage = onMessage
            )
        }
    }
}