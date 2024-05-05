package com.sarang.torang

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.sunflower.ui.TorangTheme
import com.sarang.torang.api.ApiProfile
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.di.profile_di.MyProfileScreen
import com.sarang.torang.di.profile_di.ProfileScreen
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.LoginRepositoryTest
import com.sarang.torang.repository.ProfileRepository
import com.sarang.torang.viewmodel.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var apiProfile: ApiProfile

    @Inject
    lateinit var profileRepository: ProfileRepository

    @Inject
    lateinit var feedRepository: FeedRepository

    @Inject
    lateinit var loginRepository: LoginRepository

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        Column(Modifier.height(800.dp)) {
                            ProfileNavhost(
                                onEmailLogin = {

                                },
                                onReview = {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "reviewId = ${it}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        }
                        Column(Modifier.height(300.dp)) {
                            LoginRepositoryTest(loginRepository)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileNavhost(
    onEmailLogin: () -> Unit,
    onReview: ((Int) -> Unit)? = null
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            Column {
                Button(onClick = { navController.navigate("profile/1") }) {
                    Text(text = "profile")
                }
                Button(onClick = { navController.navigate("myProfile") }) {
                    Text(text = "myProfile")
                }
                Button(onClick = { navController.navigate("myFollow") }) {
                    Text(text = "myFollow")
                }
            }
        }
        composable("profile/{id}") {
            val userId = it.arguments?.getString("id")?.toInt()
            if (userId == null) {
                Text(text = "사용자 정보가 없습니다.")
            } else {
                ProfileScreen(/*MainActivity*/
                    onSetting = {},
                    onClose = { navController.popBackStack() },
                    onEmailLogin = onEmailLogin,
                    onReview = onReview,
                    userId = userId,
                    onProfile = {
                        navController.navigate("profile/${it}")
                    }
                )
            }
        }
        composable("myProfile") {
            MyProfileScreen(
                /*MainActivity*/
                navBackStackEntry = null,
                onSetting = {},
                onEmailLogin = onEmailLogin,
                onReview = onReview,
                onProfile = {
                    navController.navigate("profile/${it}")
                }
            )
        }
        composable("myFollow") {
            MyFollowScreen(
                onBack = { navController.popBackStack() },
                onProfile = {
                    navController.navigate("profile/${it}")
                }
            )
        }
        composable("follow/{userId}") {
            val userId = it.arguments?.getString("id")?.toInt()

            if (userId != null) {
                OtherFollowScreen(
                    onBack = { navController.popBackStack() },
                    userId = userId
                )
            } else {
                Text(text = "사용자 정보가 없습니다.")
            }
        }
    }
}