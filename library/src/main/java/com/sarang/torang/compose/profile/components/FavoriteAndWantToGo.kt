package com.sarang.torang.compose.profile.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

@Composable
internal fun MyFeedsLikesFavoritiesList(
    myFeeds     : @Composable () -> Unit = {},
    favorite    : @Composable () -> Unit = {},
    like        : @Composable () -> Unit = {},
) {
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 3 })

    Scaffold(
        topBar = {
            ProfileTabs(pagerState = pagerState)
        }) {
        HorizontalPager(modifier = Modifier.padding(it) ,
                        state = pagerState ){
            when(it){
                0 -> {myFeeds.invoke()}
                1 -> {like.invoke()}
                2 -> {favorite.invoke()}
            }
        }
    }
}

fun NavHostController.move(dest: String) {
    navigate(dest) {
        // Pop up to the start destination of the graph to
        // avoid building up a large stack of destinations
        // on the back stack as users select items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }
}


@Preview
@Composable
fun PreviewMyFeedsLikesFavoritiesList() {
    MyFeedsLikesFavoritiesList(favorite = {
        Text(text = "!!!")
    }, like = {
        Text(text = "!!22")
    })
}