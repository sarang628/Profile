package com.sarang.torang.compose.follow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable

@Composable
fun MyFollowerList(
    list: List<Follow>, onDelete: (Int) -> Unit,
    onProfile: ((Int) -> Unit)? = null
) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(
                        list[it].url,
                        list[it].nickname,
                        list[it].name,
                        onButton = { onDelete.invoke(list[it].id) },
                        btnText = "Delete",
                        isMe = true,
                        onProfile = { onProfile?.invoke(list[it].id) }
                    )
                }
            })
    }
}

@Composable
fun FollowerList(
    list: List<Follow>,
    onProfile: ((Int) -> Unit)? = null
) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(
                        list[it].url,
                        list[it].nickname,
                        list[it].name,
                        onProfile = { onProfile?.invoke(list[it].id) }
                    )
                }
            })
    }
}

@Composable
fun MyFollowingList(
    list: List<Follow>,
    onUnFollow: (Int) -> Unit,
    onProfile: ((Int) -> Unit)? = null
) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(
                        list[it].url,
                        list[it].nickname,
                        list[it].name,
                        onButton = { onUnFollow.invoke(list[it].id) },
                        btnText = "Unfollow",
                        isMe = true,
                        onProfile = { onProfile?.invoke(list[it].id) }
                    )
                }
            })
    }
}

@Composable
fun FollowingList(
    list: List<Follow>, onProfile: ((Int) -> Unit)? = null
) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(
                        list[it].url,
                        list[it].nickname,
                        list[it].name,
                        onProfile = { onProfile?.invoke(list[it].id) }
                    )
                }
            })
    }
}