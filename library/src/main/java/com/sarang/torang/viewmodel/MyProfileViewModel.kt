package com.sarang.torang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.ProfileUiState
import com.sarang.torang.usecase.profile.FollowUseCase
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import com.sarang.torang.usecase.profile.IsLoginUseCase
import com.sarang.torang.usecase.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    isLoginUseCase: IsLoginUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
    private val myProfileUseCase: GetMyProfileUseCase
) : ViewModel() {
    val uiState: StateFlow<ProfileUiState> = combine(isLoginUseCase.isLogin,
                                                    myProfileUseCase.invoke()) {
        isLogin, myProfile ->
        if(!isLogin) ProfileUiState.Loading
        else ProfileUiState.Success(id = 0,
                                    name = myProfile.name,
                                    follower = myProfile.follower,
                                    following = myProfile.following)
    }.stateIn(scope = viewModelScope,
              started = SharingStarted.WhileSubscribed(5000),
              initialValue = ProfileUiState.Loading)

    fun updateProfileImage(uri: String) {

    }

    fun follow() {
        viewModelScope.launch {
            try {
                followUseCase.invoke((uiState.value as ProfileUiState.Success).id)
            } catch (e: Exception) {
            }
        }
    }

    fun unFollow() {
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke((uiState.value as ProfileUiState.Success).id)
            } catch (e: Exception) {
            }
        }
    }

    fun onClearErrorMessage() {
        viewModelScope.launch {
        }
    }

}