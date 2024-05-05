package com.sarang.torang

sealed class ProfileScreens(val route: String) {
    object Profile : ProfileScreens("Profile")
    object FriendsList : ProfileScreens("FriendsList")
}