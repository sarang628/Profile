package com.sarang.torang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.MyProfileUiState
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
    myProfileUseCase : GetMyProfileUseCase
) : ViewModel() {
    val uiState: StateFlow<MyProfileUiState> = combine(isLoginUseCase.isLogin,
                                                      myProfileUseCase.invoke()) {
        isLogin, myProfile ->
        if(!isLogin) MyProfileUiState.Login
        else myProfile
    }.stateIn(scope = viewModelScope,
              started = SharingStarted.WhileSubscribed(5000),
              initialValue = MyProfileUiState.Loading)

    fun updateProfileImage(uri: String) {

    }
}