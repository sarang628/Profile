package com.sryang.myapplication.di.profile

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.compose.composable
import androidx.navigation.get
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.testFeedUiState
import com.sarang.profile._ProfileScreen
import com.sarang.profile.uistate.ProfileUiState
import com.sarang.profile.viewmodel.ProfileService
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.repository.profile.ProfileRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ProfileRepositoryAdapterModule {
    @Provides
    fun provideProfileAdapterRepository(
        profileRepository: ProfileRepositoryImpl
    ): ProfileService {
        return object : ProfileService {
            override suspend fun loadProfile(id: Int): ProfileUiState {
                val result = profileRepository.loadProfile(id)
                return ProfileUiState(
                    profileUrl = result.profilePicUrl,
                    feedCount = result.reviewCount,
                    following = result.following,
                    follower = result.followers,
                    name = result.userName,
                    isLogin = true
                )
            }
        }
    }
}


@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel, navBackStackEntry: NavBackStackEntry) {
    val id = navBackStackEntry.arguments?.getString("id")?.toInt()


    profileViewModel.loadProfile(id!!)
    _ProfileScreen(
        profileBaseUrl = "http://sarang628.iptime.org:89/profile_images/",
        profileViewModel = profileViewModel, onLogout = {

        },
        favorite = {
            Feeds(
                list = ArrayList<FeedUiState>().apply {
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                },
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false
            )
        },
        wantToGo = {
            Feeds(
                list = ArrayList<FeedUiState>().apply {
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                    add(testFeedUiState())
                },
                onProfile = {},
                onLike = {},
                onComment = {},
                onShare = {},
                onFavorite = {},
                onMenu = { /*TODO*/ },
                onName = { /*TODO*/ },
                onRestaurant = { /*TODO*/ },
                onImage = {},
                onRefresh = { /*TODO*/ },
                isRefreshing = false
            )
        }
    )
}