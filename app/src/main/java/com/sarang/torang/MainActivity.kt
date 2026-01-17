package com.sarang.torang

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sarang.torang.api.ApiProfile
import com.sarang.torang.compose.feed.FeedScreenByReviewId
import com.sarang.torang.compose.feed.internal.components.type.LocalExpandableTextType
import com.sarang.torang.compose.feed.internal.components.type.LocalFeedImageLoader
import com.sarang.torang.compose.feed.type.FeedTypeData
import com.sarang.torang.compose.feed.type.LocalFeedCompose
import com.sarang.torang.compose.feed.type.feedType
import com.sarang.torang.compose.follow.MyFollowScreen
import com.sarang.torang.compose.follow.OtherFollowScreen
import com.sarang.torang.compose.profile.LocalProfileImage
import com.sarang.torang.compose.profile.Profile
import com.sarang.torang.di.basefeed_di.CustomExpandableTextType
import com.sarang.torang.di.basefeed_di.CustomFeedImageLoader
import com.sarang.torang.di.feed_di.CustomFeedCompose
import com.sarang.torang.di.feed_di.provideFeed
import com.sarang.torang.di.image.provideTorangAsyncImage
import com.sarang.torang.di.profile_di.MyProfileScreenNavHost
import com.sarang.torang.di.profile_di.ProvideProfileScreen
import com.sarang.torang.repository.LoginRepository
import com.sarang.torang.repository.feed.FeedFlowRepository
import com.sarang.torang.repository.feed.FeedLoadRepository
import com.sarang.torang.repository.feed.FeedRepository
import com.sarang.torang.repository.test.LoginRepositoryTest
import com.sarang.torang.repository.test.feed.FeedRepositoryTest
import com.sryang.torang.ui.TorangTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var apiProfile: ApiProfile
    @Inject lateinit var feedRepository: FeedRepository
    @Inject lateinit var feedFlowRepository: FeedFlowRepository
    @Inject lateinit var feedLoadRepository: FeedLoadRepository
    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TorangTheme {
                Surface(modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)) {
                        ProfileTestMenu(loginRepository = loginRepository)
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
                Menu(navController)
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
                    onSetting           = { },
                    onEmailLogin        = { },
                    onClose             = { navController.popBackStack() },
                    onReview            = { navController.navigate("myReview/$it") },
                    navController       = rememberNavController(),
                    onMessage           = { },
                    contentWindowInsets = WindowInsets(0)
                )
            }
            composable("myFollow/{page}") {
                CompositionLocalProvider(LocalProfileImage provides {provideTorangAsyncImage().invoke(
                    it.modifier,
                    it.url,
                    it.errorIconSize,
                    it.progressSize,
                    it.contentScale)}) {
                    MyFollowScreen(onBack       = { navController.popBackStack() },
                        onProfile    = { navController.navigate("profile/${it}") },
                        page         = it.arguments?.getString("page")?.toInt())
                }
            }
            composable("follow/{userId}") {
                val userId = it.arguments?.getString("userId")?.toInt()

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
                    CompositionLocalProvider(LocalFeedImageLoader provides CustomFeedImageLoader(),
                        LocalExpandableTextType provides CustomExpandableTextType,
                        LocalFeedCompose provides CustomFeedCompose) {
                        FeedScreenByReviewId(reviewId = it)
                    }
                }
            }
            composable("FeedRepositoryTest") {
                FeedRepositoryTest(feedRepository = feedRepository,
                                   feedLoadRepository = feedLoadRepository,
                                   feedFlowRepository = feedFlowRepository)
            }
        }
    }
}

@Composable
fun Menu(navController : NavHostController){
    Column {
        Button(onClick = { navController.navigate("profile/1") }) {
            Text(text = "profile")
        }
        Button(onClick = { navController.navigate("myProfile") }) {
            Text(text = "myProfile")
        }
        Button(onClick = {
            navController.navigate("myFollow/0")
        }) {
            Text(text = "myFollow")
        }
        Button(onClick = {
            navController.navigate("follow/2")
        }) {
            Text(text = "follow")
        }
        Button(onClick = { navController.navigate("LoginRepositoryTest") }) {
            Text(text = "LoginRepositoryTest")
        }
        Button(onClick = {navController.navigate("FeedRepositoryTest")}) {
            Text(text = "FeedRepositoryTest")
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