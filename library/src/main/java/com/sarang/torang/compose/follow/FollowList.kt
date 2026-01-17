package com.sarang.torang.compose.follow

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MyFollowerList(list     : List<Follow>  = listOf(),
                   onDelete : (Int) -> Unit = {},
                   onProfile: (Int) -> Unit = {}) {
    Column {
        LazyColumn(content = {
                items(list.size) {
                    ItemFollow(url = list[it].url,
                               id = list[it].nickname,
                               name = list[it].name,
                               onRightButton = { onDelete.invoke(list[it].id) },
                               rightButtonText = "Delete",
                               showRightButton = true,
                               onProfile = { onProfile.invoke(list[it].id) } )
                }})
    }
}

@Preview(showBackground = true)
@Composable
fun FollowerList(list: List<Follow> = listOf(),
                 onProfile: (Int) -> Unit = {} ) {
    LazyColumn(
        content = {
            items(list.size) {
                ItemFollow(url = list[it].url,
                           id = list[it].nickname,
                           name = list[it].name,
                           onProfile = { onProfile.invoke(list[it].id) })
            }
        })
}

@Composable
fun MyFollowingList(list        : List<Follow>  = listOf(),
                    onUnFollow  : (Int) -> Unit = { },
                    onProfile   : (Int) -> Unit = { },
) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(url = list[it].url,
                               id = list[it].nickname,
                               name = list[it].name,
                               onRightButton = { onUnFollow.invoke(list[it].id) },
                               rightButtonText = "Unfollow",
                               showRightButton = true,
                               onProfile = { onProfile.invoke(list[it].id) })
                }
            })
    }
}

@Composable
fun FollowingList(list: List<Follow> = listOf(),
                  onProfile: (Int) -> Unit = {  }) {
    Column {
        LazyColumn(
            content = {
                items(list.size) {
                    ItemFollow(url = list[it].url,
                               id = list[it].nickname,
                               name = list[it].name,
                               onProfile = { onProfile.invoke(list[it].id) })
                }
            })
    }
}