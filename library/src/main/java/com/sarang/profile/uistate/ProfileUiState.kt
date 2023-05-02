package com.sarang.profile.uistate

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class ProfileUiState(
    val profileUrl: String? = null,
    val feedCount: Int? = null,
    val follower: Int? = null,
    val following: Int? = null,
    val isLogin: Boolean = false
)

fun testProfileUiState(lifecycleOwner: LifecycleOwner): StateFlow<ProfileUiState> {
    val flow = MutableStateFlow(ProfileUiState())
    val delay = 500L

    lifecycleOwner.lifecycleScope.launch {
        while (true) {
            flow.emit(testProfileImageOn()); delay(delay); flow.emit(testProfileImageOff()); delay(delay);
            flow.emit(testProfileFeedCountOn()); delay(delay); flow.emit(testProfileFeedCountOff()); delay(delay);
            flow.emit(testProfileFollowerOn()); delay(delay); flow.emit(testProfileFollowerOff()); delay(delay);
            flow.emit(testProfileFollowingOn()); delay(delay); flow.emit(testProfileFollowingOff()); delay(delay);
            flow.emit(testloginOn()); delay(delay); flow.emit(testloginOff()); delay(delay);
        }
    }
    return flow
}


//로그인 페이지에서 프로필 화면으로 이동 테스트
fun testProfileUiState1(lifecycleOwner: LifecycleOwner): StateFlow<ProfileUiState> {
    val flow = MutableStateFlow(ProfileUiState())
    val delay = 1000L

    lifecycleOwner.lifecycleScope.launch {
        while (true) {
            delay(delay); flow.emit(testloginOn());
        }
    }
    return flow
}

fun testProfileImageOn(): ProfileUiState {
    return ProfileUiState(
        profileUrl = "http://sarang628.iptime.org:88/2.png",
        isLogin = true
    )
}

fun testProfileImageOff(): ProfileUiState {
    return ProfileUiState(
        profileUrl = "",
        isLogin = true
    )
}

fun testProfileFeedCountOn(): ProfileUiState {
    return ProfileUiState(
        feedCount = 10,
        isLogin = true
    )
}

fun testProfileFeedCountOff(): ProfileUiState {
    return ProfileUiState(
        feedCount = 0,
        isLogin = true
    )
}

fun testProfileFollowerOn(): ProfileUiState {
    return ProfileUiState(
        follower = 10,
        isLogin = true
    )
}

fun testProfileFollowerOff(): ProfileUiState {
    return ProfileUiState(
        follower = 0,
        isLogin = true
    )
}

fun testProfileFollowingOn(): ProfileUiState {
    return ProfileUiState(
        following = 10,
        isLogin = true
    )
}

fun testProfileFollowingOff(): ProfileUiState {
    return ProfileUiState(
        following = 0,
        isLogin = true
    )
}

fun testloginOn(): ProfileUiState {
    return ProfileUiState(
        isLogin = true
    )
}

fun testloginOff(): ProfileUiState {
    return ProfileUiState(
        isLogin = false
    )
}