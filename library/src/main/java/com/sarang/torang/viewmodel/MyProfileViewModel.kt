package com.sarang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.ProfileUiState
import com.sarang.torang.usecase.profile.FollowUseCase
import com.sarang.torang.usecase.profile.IsLoginUseCase
import com.sarang.torang.usecase.profile.ProfileService
import com.sarang.torang.usecase.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    private val service: ProfileService,
    private val isLoginUseCase: IsLoginUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase,
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginUseCase.isLogin

    init {
        viewModelScope.launch {
            isLogin.distinctUntilChanged().collect {
                if (it) loadProfileByToken()
            }
        }
    }

    fun loadProfileByToken() {
        viewModelScope.launch {
            try {
                _uiState.emit(service.loadProfileByToken())

                service.getFavorites().collect { favs ->
                    _uiState.update { (it as ProfileUiState.Success).copy(favoriteList = favs) }
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", e.toString())
            }
        }
    }

    fun updateProfileImage(uri: String) {
        viewModelScope.launch {
            service.updateProfile(uri)
            loadProfileByToken()
        }
    }

    fun follow() {
        viewModelScope.launch {
            try {
                followUseCase.invoke((uiState.value as ProfileUiState.Success).id)
                _uiState.update { (it as ProfileUiState.Success).copy(isFollow = true) }
            } catch (e: Exception) {
                _uiState.update {
                    (it as ProfileUiState.Success).copy(errorMessage = e.toString())
                }
            }
        }
    }

    fun unFollow() {
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke((uiState.value as ProfileUiState.Success).id)
                _uiState.update { (it as ProfileUiState.Success).copy(isFollow = false) }
            } catch (e: Exception) {
                _uiState.update {
                    (it as ProfileUiState.Success).copy(errorMessage = e.toString())
                }
            }
        }
    }

    fun onClearErrorMessage() {
        viewModelScope.launch {
            _uiState.update { (it as ProfileUiState.Success).copy(errorMessage = null) }
        }
    }

}