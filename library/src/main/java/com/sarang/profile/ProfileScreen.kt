package com.sarang.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.library.JsonToObjectGenerator
import com.example.screen_feed.ui.PreviewFeeds
import com.sarang.profile.uistate.ProfileUiState
import kotlinx.coroutines.flow.MutableStateFlow


@Composable
fun ProfileScreen(uiState: ProfileUiState) {

    Box(
        modifier = Modifier
            .background(colorResource(id = R.color.colorSecondaryLight))
            .fillMaxHeight()
    )
    {

        Row() {
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.ic_settings), contentDescription = "")
        }

        Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 20.dp)) {
            ProfileSummary(
                profileUrl = uiState.profileUrl,
                feedCount = uiState.feedCount,
                follower = uiState.follower,
                following = uiState.following
            )
            Spacer(modifier = Modifier.height(50.dp))
            Row {
                Button(
                    onClick = { },
                    modifier = Modifier
                        .weight(1f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.colorPrimary))
                ) {
                    Text(
                        text = "프로필 편집",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            FavoriteAndWantToGo()
        }
    }
}

@Composable
fun FavoriteAndWantToGo() {
    val navController = rememberNavController()

    val tabContents = listOf(
        "즐겨찾기",
        "가고싶다",
    )
    var selectedIndex by remember { mutableStateOf(0) }
    val state = MutableStateFlow(true)
    val isFavorite by state.collectAsState()

    Scaffold(topBar = {
        PreviewTabs(
            isFavorite = isFavorite,
            onFavorite = {
                navController.navigate("profile")
            },
            onWantToGo = {
                navController.navigate("finding")
            })
    }) {
        Column(Modifier.background(colorResource(id = R.color.colorSecondaryLight))) {
            Text(text = "", Modifier.padding(it))
            Spacer(modifier = Modifier.height(2.dp))
            NavHost(navController = navController, startDestination = "profile") {
                composable("profile") {
                    PreviewFeeds()
                    LaunchedEffect(key1 = "1") {
                        state.emit(false)
                    }
                }
                composable("finding") {
                    PreviewFeeds()
                    LaunchedEffect(key1 = "2") {
                        state.emit(true)
                    }
                }
            }
        }
    }
}

@Composable
fun PreviewTabs(
    isFavorite: Boolean,
    onFavorite: () -> Unit,
    onWantToGo: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .background(colorResource(id = R.color.colorSecondaryLight))
    ) {
        Column(
            Modifier
                .weight(1f)
                .height(42.dp)
                .clickable { onFavorite.invoke() }
        )
        {
            Text(
                text = "즐겨찾기",
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .wrapContentHeight(), textAlign = TextAlign.Center
            )
            if (isFavorite)
                Spacer(
                    modifier =
                    Modifier
                        .background(colorResource(id = R.color.colorPrimary))
                        .fillMaxWidth()
                        .height(2.dp)
                )
        }
        Column(
            Modifier
                .weight(1f)
                .height(42.dp)
                .clickable { onFavorite.invoke() }
        )
        {
            Text(
                text = "가고싶다",
                Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .wrapContentHeight(), textAlign = TextAlign.Center
            )
            if (!isFavorite)
                Spacer(
                    modifier =
                    Modifier
                        .background(colorResource(id = R.color.colorPrimary))
                        .fillMaxWidth()
                        .height(2.dp)
                )
        }
    }
}


@Composable
fun ProfileSummary(
    profileUrl: String?,
    feedCount: Int?,
    follower: Int?,
    following: Int?
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            model = profileUrl ?: "",
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )

        Row(
            Modifier
                .weight(1f)
                .padding(start = 16.dp)
        ) {
            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = feedCount.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "게시물", fontSize = 18.sp)
            }

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = follower.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "팔로워", fontSize = 18.sp)
            }

            Column(Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally)
            {
                Text(
                    text = following.toString(),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(text = "팔로잉", fontSize = 18.sp)
            }
        }

    }
}


@Preview
@Composable
fun PreviewProfile() {
    val uiState: ProfileUiState = JsonToObjectGenerator<ProfileUiState>().getObjectByFile(
        context = LocalContext.current,
        "profile.json",
        ProfileUiState::class.java
    )
    ProfileScreen(uiState = uiState)
}
