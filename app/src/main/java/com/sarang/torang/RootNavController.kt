package com.sarang.torang

import androidx.navigation.NavHostController

class RootNavController {

    var navController : NavHostController? = null
    fun editProfileImage() {

    }

    fun popBackStack() {
        navController?.popBackStack()
    }

    fun myReview(it: Int) {
        navController?.navigate("myReview/${it}")
    }

    fun review(it: Int) {

    }
}