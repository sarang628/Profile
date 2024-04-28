package com.sarang.torang.compose.follow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodel.FollowViewModel


@Composable
fun FollowScreen(
    followViewModel: FollowViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val follower by followViewModel.follower.collectAsState()
    val following by followViewModel.following.collectAsState()
    val subscription by followViewModel.subscription.collectAsState()
    val uiState by followViewModel.uiState.collectAsState()
    val errorMessage by followViewModel.errorMessage.collectAsState()
    _FollowScreen(
        name = uiState.name,
        following = uiState.following,
        follower = uiState.follower,
        subscription = uiState.subscription,
        followerList = follower,
        followingList = following,
        subscriptionList = subscription,
        errorMessage = errorMessage,
        onClearErrorMessage = { followViewModel.clearErrorMessage() },
        onBack = onBack,
        onUnFollow = { followViewModel.unFollow(it) },
        onDelete = { followViewModel.delete(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun _FollowScreen(
    name: String,
    following: Int,
    follower: Int,
    subscription: Int,
    followerList: List<Follow>,
    followingList: List<Follow>,
    subscriptionList: List<Follow>,
    errorMessage: String? = null,
    onClearErrorMessage: () -> Unit,
    onBack: () -> Unit,
    onDelete: (Int) -> Unit,
    onUnFollow: (Int) -> Unit
) {
    Scaffold(
        topBar = { FollowTopAppBar(name, onBack = onBack) },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            var state by remember { mutableIntStateOf(0) }
            val titles =
                listOf("$follower followers", "$following following", "$subscription subscription")
            Column {
                PrimaryTabRow(selectedTabIndex = state) {
                    titles.forEachIndexed { index, title ->
                        Tab(
                            selected = state == index,
                            onClick = { state = index },
                            text = {
                                Text(
                                    text = title,
                                    maxLines = 2,
                                    overflow = TextOverflow.Ellipsis
                                )
                            }
                        )
                    }
                }
                if (state == 0) {
                    Follow(
                        list = followerList,
                        isFollower = true,
                        onDelete = onDelete,
                        onUnFollow = onUnFollow
                    )
                } else if (state == 1) {
                    Follow(
                        list = followingList,
                        isFollower = false,
                        onDelete = onDelete,
                        onUnFollow = onUnFollow
                    )
                } else {
                    Follow(
                        list = subscriptionList,
                        isFollower = false,
                        onDelete = onDelete,
                        onUnFollow = onUnFollow
                    )
                }
            }
            errorMessage?.let {
                AlertDialog(onDismissRequest = { /*TODO*/ }, confirmButton = {
                    Button(onClick = { onClearErrorMessage.invoke() }) {
                        Text(text = "확인")
                    }
                }, title = {
                    Text(text = it, fontSize = 14.sp)
                })
            }
        }
    }
}

@Composable
fun Follow(
    list: List<Follow>, isFollower: Boolean, onDelete: (Int) -> Unit,
    onUnFollow: (Int) -> Unit
) {
    Column {
        LazyColumn(
            content = {
                isFollower
                items(list.size) {
                    ItemFollow(
                        list[it].url,
                        list[it].nickname,
                        list[it].name,
                        onFollow = {},
                        isFollower = isFollower,
                        onDelete = { onDelete.invoke(list[it].id) },
                        onUnFollow = { onUnFollow.invoke(list[it].id) }
                    )
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FollowTopAppBar(name: String, onBack: () -> Unit) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    TopAppBar(
        modifier = Modifier.height(50.dp),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Box(modifier = Modifier.fillMaxHeight()) {
                Text(
                    name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.align(Alignment.CenterStart),
                    fontSize = 20.sp
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = { onBack.invoke() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "Localized description"
                )
            }
        },
        scrollBehavior = scrollBehavior,
    )
}

@Preview
@Composable
fun PreViewFollowScreen() {
    _FollowScreen(
        name = "torang110113",
        following = 10,
        follower = 11,
        subscription = 13,
        followingList = ArrayList(),
        followerList = ArrayList(),
        subscriptionList = ArrayList(),
        onClearErrorMessage = {},
        onBack = {},
        onUnFollow = {},
        onDelete = {}
    )
}