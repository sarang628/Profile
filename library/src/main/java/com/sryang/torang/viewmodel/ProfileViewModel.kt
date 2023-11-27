package com.sryang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang.uistate.ProfileUiState
import com.sryang.torang.usecase.FollowUseCase
import com.sryang.torang.usecase.IsLoginUseCase
import com.sryang.torang.usecase.ProfileService
import com.sryang.torang.usecase.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val service: ProfileService,
    private val isLoginUseCase: IsLoginUseCase,
    private val followUseCase: FollowUseCase,
    private val unFollowUseCase: UnFollowUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        ProfileUiState(
            id = 0,
            profileUrl = "",
            feedCount = 0,
            follower = 0,
            following = 0,
            name = ""
        )
    )
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginUseCase.isLogin

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            try {
                val result = service.loadProfile(id)
                _uiState.emit(result.copy(id = id))
                service.getFavorites().collect { favs ->
                    _uiState.update { it.copy(favoriteList = favs) }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.toString())
                }
            }

        }
    }

    fun loadProfileByToken() {
        viewModelScope.launch {
            try {
                _uiState.emit(service.loadProfileByToken())

                service.getFavorites().collect { favs ->
                    _uiState.update { it.copy(favoriteList = favs) }
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
                followUseCase.invoke(uiState.value.id)
                _uiState.update { it.copy(isFollow = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.toString())
                }
            }
        }
    }

    fun unFollow() {
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke(uiState.value.id)
                _uiState.update { it.copy(isFollow = false) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.toString())
                }
            }
        }
    }

    fun onClearErrorMessage() {
        viewModelScope.launch {
            _uiState.update { it.copy(errorMessage = null) }
        }
    }

}