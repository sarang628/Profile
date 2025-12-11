package com.sarang.torang.compose.profile

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sarang.torang.R
import com.sarang.torang.compose.myfeed.MyFavoriteListScreen
import com.sarang.torang.compose.myfeed.MyFeedListScreen
import com.sarang.torang.compose.myfeed.MyLikeListScreen
import com.sarang.torang.compose.profile.components.MyFeedsLikesFavoritiesList
import com.sarang.torang.compose.profile.components.ProfileSummary
import com.sarang.torang.compose.profile.components.ProfileTabs
import com.sarang.torang.viewmodel.profile.MyProfileViewModel

@Composable
fun MyProfileScreen(myProfileViewModel: MyProfileViewModel,
                    onSetting         : () -> Unit      = {},
                    onEditProfile     : () -> Unit      = {},
                    onFollowing       : () -> Unit      = {},
                    onFollower        : () -> Unit      = {},
                    onWrite           : () -> Unit      = {},
                    onEmailLogin      : () -> Unit      = {},
                    onReview          : (Int) -> Unit   = {}) {
    val uiState by myProfileViewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        is MyProfileUiState.Success -> {
            _MyProfileScreen(onSetting     = onSetting,
                            onEditProfile  = onEditProfile,
                            uiState        = uiState as MyProfileUiState.Success,
                            onWrite        = onWrite,
                            onFollowing    = onFollowing,
                            onFollower     = onFollower,
                            feedScreenList = { MyFeedListScreen(modifier = it, onReview = onReview) },
                            favoriteList   = { MyFavoriteListScreen(modifier = it, onReview = onReview) },
                            likeList       = { MyLikeListScreen (modifier = it, onReview = onReview) })
        }

        is MyProfileUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(Modifier.align(Alignment.Center))
            }
        }

        is MyProfileUiState.Error -> {}
        is MyProfileUiState.NeedLogin -> { Login(onEmailLogin) }
    }
}

@Preview(showBackground = true)
@Composable
internal fun Login(onEmailLogin : () -> Unit = {}){
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.Center),
               horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "로그인을 해주세요.")
            Button(onClick = { onEmailLogin.invoke() }) {
                Text(text = "LOG IN WITH EMAIL")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Preview(showBackground = true)
@Composable
private fun _MyProfileScreen(uiState        : MyProfileUiState.Success = MyProfileUiState.Success(),
                            onSetting       : () -> Unit               = {},
                            onEditProfile   : () -> Unit               = {},
                            onFollowing     : () -> Unit               = {},
                            onFollower      : () -> Unit               = {},
                            onWrite         : () -> Unit               = {},
                            feedScreenList  : @Composable (Modifier) -> Unit   = {},
                            favoriteList    : @Composable (Modifier) -> Unit   = {},
                            likeList        : @Composable (Modifier) -> Unit   = {}) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })
    val topBar = @Composable {
        TopAppBar( title          = { Text(text = uiState.name) },
                   scrollBehavior = scrollBehavior,
                   actions        = { IconButton({onSetting.invoke()}) {
                                        Icon(painter = painterResource(id = R.drawable.ic_settings),
                                             contentDescription = "",
                                             tint = MaterialTheme.colorScheme.primary)}})}

    Scaffold(modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
             topBar = topBar) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            LazyColumn(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                item {
                    ProfileSummary(
                                   profileUrl   = uiState.profileUrl,
                                   feedCount    = uiState.feedCount,
                                   follower     = uiState.follower,
                                   following    = uiState.following,
                                   name         = uiState.name,
                                   onFollower    = onFollower,
                                   onFollowing  = onFollowing,
                                   onWrite      = onWrite)
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
                item {
                    Row {
                        Button(onClick = { onEditProfile.invoke() },
                            modifier = Modifier
                                .weight(1f)
                                .height(35.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "프로필 편집",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp)
                        }
                        Spacer(Modifier.width(8.dp))
                        Button(onClick = {  },
                            modifier = Modifier
                                .weight(1f)
                                .height(35.dp),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(text = "프로필 공유",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp)
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(5.dp))
                }
                stickyHeader {
                    ProfileTabs(pagerState = pagerState)
                }
                item {
                    MyFeedsLikesFavoritiesList(
                        modifier    = Modifier.fillParentMaxSize()
                                              .nestedScroll(scrollBehavior.nestedScrollConnection),
                        pagerState  = pagerState,
                        myFeeds     = { feedScreenList.invoke(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) },
                        like        = { likeList.invoke(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) },
                        favorite    = { favoriteList.invoke(Modifier.nestedScroll(scrollBehavior.nestedScrollConnection)) }
                    )
                }
            }
        }
    }
}