package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.api.ApiProfile
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.compose.profile.Profile
import com.sarang.torang.di.feed_di.provideFeed
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.profile_di.ProvideProfileScreen
import com.sarang.torang.repository.FeedRepository
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var apiProfile: ApiProfile

    @Inject lateinit var feedRepository: FeedRepository
    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                        Column(Modifier.height(800.dp)) {
                            ProfileTestMenu(loginRepository = loginRepository)
                        }
                }
            }
        }
    }
}
@Composable
fun ProfileTestMenu(loginRepository : LoginRepository) {
    val navController = rememberNavController()
    val rootNavController = RootNavController()
    rootNavController.navController = navController
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
                Button(onClick = { navController.navigate("LoginRepositoryTest") }) {
                    Text(text = "LoginRepositoryTest")
                }
            }
        }
        composable("LoginRepositoryTest"){
            LoginRepositoryTest(loginRepository)
        }
        composable("profile/{id}") {
            it.arguments?.getString("id")?.toInt()?.let {
                ProvideProfileScreen(id = it,
                                     rootNavController = rootNavController)
            }
        }
        composable("myProfile") {
            MyProfileScreenNavHost(/*MainActivity*/
                onSetting = {},
                onEmailLogin = { },
                onClose = { navController.popBackStack() },
                onReview = {},
                navController = rememberNavController(),
                onMessage = {}
            )
        }
        composable("myFollow/{page}}") {
            MyFollowScreen(onBack = { navController.popBackStack() },
                           onProfile = { navController.navigate("profile/${it}") },
                           page = it.arguments?.getString("page")?.toInt())
        }
        composable("follow/{userId}") {
            val userId = it.arguments?.getString("id")?.toInt()

            if (userId != null) {
                CompositionLocalProvider(LocalProfileImage provides {
                    provideTorangAsyncImage().invoke(it.modifier,
                        it.url,
                        it.progressSize,
                        it.errorIconSize,
                        it.contentScale) }) {
                    OtherFollowScreen(onBack = { navController.popBackStack() },
                                      userId = userId)
                }
            } else {
                Text(text = "사용자 정보가 없습니다.")
            }
        }
        composable("myReview/{reviewId}"){
            val reviewId = it.arguments?.getString("reviewId")?.toInt()
            Text("$reviewId")
            provideFeed().invoke(FeedTypeData())
            reviewId?.let {
                FeedScreenByReviewId(reviewId = it)
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfile() {
    TorangTheme {
        Profile()
    }
}