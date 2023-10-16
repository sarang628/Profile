package com.sarang.profile.viewmodel

import android.util.Log
import android.util.LogPrinter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.profile.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val service: ProfileService
) : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProfileUiState(
            profileUrl = "",
            feedCount = 0,
            follower = 0,
            following = 0,
            name = ""
        )
    )

    val uiState = _uiState.asStateFlow()

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            val result = service.loadProfile(id)
            _uiState.emit(
                result
            )

            service.getFavorites().collect {
                Log.d("ProfileViewModel", "getFavorites:$it")
                _uiState.emit(
                    uiState.value.copy(favoriteList = it)
                )
            }
        }
    }

    fun loadProfileByToken() {
        viewModelScope.launch {
            try {
                val result = service.loadProfileByToken()
                _uiState.emit(
                    result
                )

                service.getFavorites().collect {
                    Log.d("ProfileViewModel", "getFavorites:$it")
                    _uiState.emit(
                        uiState.value.copy(favoriteList = it)
                    )
                }
            } catch (e: Exception) {
                Log.e("ProfileViewModel", e.toString())
            }
        }
    }

    fun updateProfileImage(id: Int, uri: String) {
        viewModelScope.launch {
            service.updateProfile(id, uiState.value.name, uri)
            loadProfile(id)
        }
    }

}