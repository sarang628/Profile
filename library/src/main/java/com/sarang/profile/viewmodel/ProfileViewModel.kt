package com.sarang.profile.viewmodel

import androidx.compose.runtime.collectAsState
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
    private val repository: ProfileService
) : ViewModel() {

    val _uiState = MutableStateFlow(
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
            val result = repository.loadProfile(id)
            _uiState.emit(
                result
            )

            repository.getFavorites().collect {
                _uiState.emit(
                    uiState.value.copy(favoriteList = it)
                )
            }
        }
    }

}