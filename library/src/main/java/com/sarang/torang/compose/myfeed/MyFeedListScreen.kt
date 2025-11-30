package com.sarang.torang.compose.myfeed

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.sarang.torang.compose.LocalProfileImage
import com.sarang.torang.compose.ProfileImageTypeData
import com.sarang.torang.reviewImage
import com.sarang.torang.viewmodel.MyFeedListViewModel

@Composable
fun MyFeedListScreen(feedListViewModel: MyFeedListViewModel = hiltViewModel(),
                     onReview: (Int) -> Unit = { Log.w("_FeedListScreen", "onReview is null")  }) {

    val list by feedListViewModel.list.collectAsState()

    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        columns = GridCells.Fixed(3),
        content = {
            items(list.size) {
                LocalProfileImage.current.invoke(
                    ProfileImageTypeData(
                        modifier = Modifier.height(130.dp)
                                           .padding(0.5.dp)
                                           .clickable { onReview.invoke(list[it].reviewId) },
                        url = list[it].reviewImage,
                        errorIconSize = 50.dp,
                        progressSize = 50.dp,
                        contentScale = ContentScale.Crop
                    )
                )
            }
        })
}
