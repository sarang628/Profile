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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodel.MyFollowViewModel


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
    onUnFollow: (Int) -> Unit,
    isMe: Boolean = false,
    onProfile: ((Int) -> Unit)? = null,
    page: Int? = null,
    image: @Composable (Modifier, String, Dp?, Dp?, ContentScale?) -> Unit,
) {
    Scaffold(
        topBar = { FollowTopAppBar(name, onBack = onBack) },
    ) { innerPadding ->
        Column(Modifier.padding(innerPadding)) {
            var state by remember { mutableIntStateOf(page ?: 0) }
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
                    if (isMe) {
                        MyFollowerList(
                            list = followerList,
                            onDelete = onDelete,
                            onProfile = onProfile,
                            image = image
                        )
                    } else {
                        FollowerList(
                            list = followerList,
                            onProfile = onProfile,
                            image = image
                        )
                    }
                } else if (state == 1) {
                    if (isMe) {
                        MyFollowingList(
                            list = followingList,
                            onUnFollow = onUnFollow,
                            onProfile = onProfile,
                            image = image
                        )
                    } else {
                        FollowingList(
                            list = followingList,
                            onProfile = onProfile,
                            image = image
                        )
                    }
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
    _FollowScreen(/*Preview*/
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
        onDelete = {},
        image = { _, _, _, _, _ -> }
    )
}