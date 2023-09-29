package com.sryang.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sarang.base_feed.ui.Feeds
import com.sarang.base_feed.uistate.FeedUiState
import com.sarang.base_feed.uistate.testFeedUiState
import com.sarang.profile.ProfileScreen
import com.sarang.profile.viewmodel.ProfileViewModel
import com.sryang.torang_repository.api.ApiProfile
import com.sryang.torang_repository.repository.profile.ProfileRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val profileViewModel: ProfileViewModel by viewModels()

    @Inject
    lateinit var apiProfile: ApiProfile

    @Inject
    lateinit var profileRepository: ProfileRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            profileViewModel.loadProfile(0)
            ProfileScreen(
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
    }
}