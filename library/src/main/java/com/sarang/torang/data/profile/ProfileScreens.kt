package com.sarang.torang.data.profile

sealed class ProfileScreens(val route: String) {
    object Profile : ProfileScreens("Profile")
    object FriendsList : ProfileScreens("FriendsList")
}