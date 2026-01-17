package com.sarang.torang.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.compose.profile.ProfileUiState
import com.sarang.torang.usecase.profile.FindOrCreateChatRoomByUserIdUseCase
import com.sarang.torang.usecase.profile.FollowUseCase
import com.sarang.torang.usecase.profile.IsLoginUseCase
import com.sarang.torang.usecase.profile.ProfileService
import com.sarang.torang.usecase.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val tag = "__ProfileViewModel"

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val service                             : ProfileService,
    private val isLoginUseCase                      : IsLoginUseCase,
    private val followUseCase                       : FollowUseCase,
    private val unFollowUseCase                     : UnFollowUseCase,
    private val findOrCreateChatRoomByUserIdUseCase : FindOrCreateChatRoomByUserIdUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<ProfileUiState> =
        MutableStateFlow(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()
    val isLogin = isLoginUseCase.isLogin

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            try {
                val result = service.loadProfile(id)
                _uiState.emit((result as ProfileUiState.Success).copy(id = id))
            } catch (e: Exception) {
                Log.e(tag, e.toString())
                _uiState.update {
                    ProfileUiState.Error(message = e.toString())
                }
            }

        }
    }

    fun follow() {
        viewModelScope.launch {
            try {
                followUseCase.invoke((uiState.value as ProfileUiState.Success).id)
                _uiState.update { (it as ProfileUiState.Success).copy(isFollow = true) }
            } catch (e: Exception) {

            }
        }
    }

    fun unFollow() {
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke((uiState.value as ProfileUiState.Success).id)
                _uiState.update { (it as ProfileUiState.Success).copy(isFollow = false) }
            } catch (e: Exception) {

            }
        }
    }

    suspend fun findOrCreateChatRoomByUserId(userId: Int): Int {
        return findOrCreateChatRoomByUserIdUseCase.invoke(userId)
    }

}