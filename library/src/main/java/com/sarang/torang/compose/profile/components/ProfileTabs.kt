package com.sarang.torang.compose.profile.components

import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
internal fun ProfileTabs(
    pagerState : PagerState = rememberPagerState(0) { 0 },
) {
    val coroutineScope = rememberCoroutineScope()
    TabRow(selectedTabIndex = pagerState.currentPage) {
        IconButton({
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }) {
            Icon(Icons.AutoMirrored.Default.List, null)
        }

        IconButton({
            coroutineScope.launch {
                pagerState.animateScrollToPage(1)
            }
        }) {
            Icon(Icons.Rounded.Favorite, null)
        }

        IconButton({
            coroutineScope.launch {
                pagerState.animateScrollToPage(2)
            }
        }) {
            Icon(Icons.Rounded.Star, null)
        }
    }
}