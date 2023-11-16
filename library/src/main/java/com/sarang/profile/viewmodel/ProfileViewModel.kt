package com.sarang.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.profile.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val service: ProfileService
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileUiState(profileUrl = "", feedCount = 0, follower = 0, following = 0, name = ""))
    val uiState = _uiState.asStateFlow()

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            val result = service.loadProfile(id)
            _uiState.emit(result)

            service.getFavorites().collect { favs ->
                _uiState.update { it.copy(favoriteList = favs) }
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

}