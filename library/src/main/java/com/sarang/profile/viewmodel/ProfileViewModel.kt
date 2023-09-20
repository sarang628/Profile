package com.sarang.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.profile.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    val uiState = MutableStateFlow(
        ProfileUiState(
            profileUrl = "",
            feedCount = 0,
            follower = 0,
            following = 0,
            name = ""
        )
    )

    fun loadProfile(id: Int) {
        viewModelScope.launch {
            val result = repository.loadProfile(id)
            uiState.emit(
                uiState.value.copy(
                    profileUrl = result.user.profilePictureUrl,
                    name = result.user.name,
                    feedCount = result.post,
                    follower = result.follower,
                    following = result.following

                )
            )
        }
    }
}