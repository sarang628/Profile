package com.sarang.torang.compose.myfeed

import TorangAsyncImage1
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.viewmodel.MyFeedListViewModel

@Composable
fun FeedListScreen(
    feedListViewModel: MyFeedListViewModel = hiltViewModel(),
    userId: Int,
    onReview: ((Int) -> Unit)? = null
) {
    LaunchedEffect(key1 = userId, block = {
        Log.d("__FeedListScreen", "userId: ${userId}")
        feedListViewModel.load(userId)
    })
    val list by feedListViewModel.list.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        content = {
            items(list.size) {
                Log.d("_FeedListScreen", list[it].reviewImage[0])
                TorangAsyncImage1(
                    modifier = Modifier
                        .height(130.dp)
                        .padding(0.5.dp)
                        .clickable {
                            if (onReview == null) {
                                Log.w("_FeedListScreen", "onReview is null")
                            }
                            onReview?.invoke(list[it].reviewId)
                        },
                    model = list[it].reviewImage[0],
                )
            }
        })
}
