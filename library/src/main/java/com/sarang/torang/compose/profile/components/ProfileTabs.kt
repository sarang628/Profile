package com.sarang.torang.compose.profile.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
            Icon(Icons.Rounded.AccountCircle, null)
        }

        IconButton({
            coroutineScope.launch {
                pagerState.animateScrollToPage(1)
            }
        }) {
            Icon(Icons.Rounded.Face, null)
        }

        IconButton({
            coroutineScope.launch {
                pagerState.animateScrollToPage(2)
            }
        }) {
            Icon(Icons.Rounded.Favorite, null)
        }
    }
}