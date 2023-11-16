package com.sarang.profile.compose.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun FavoriteAndWantToGo(
    favorite: @Composable () -> Unit,
    wantToGo: @Composable () -> Unit
) {
    val navController = rememberNavController()
    var isFavorite by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            ProfileTabs(
                isFavorite = isFavorite,
                onFavorite = {
                    isFavorite = true
                    navController.move("favorite")
                },
                onWantToGo = {
                    isFavorite = false
                    navController.move("wantToGo")
                })
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            NavHost(
                navController = navController, startDestination = "favorite"
            ) {
                composable("favorite") {
                    favorite.invoke()
                }
                composable("wantToGo") {
                    wantToGo.invoke()
                }
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
fun PreviewFavoriteAndWantToGo() {
    FavoriteAndWantToGo(favorite = {
        Text(text = "!!!")
    }, wantToGo = {
        Text(text = "!!22")
    })
}