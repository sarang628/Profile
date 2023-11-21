package com.sarang.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.profile.compose.follow.Follow
import com.sarang.profile.usecase.DeleteUseCase
import com.sarang.profile.usecase.GetFollowerUseCase
import com.sarang.profile.usecase.GetFollowingUseCase
import com.sarang.profile.usecase.GetProfileUseCase
import com.sarang.profile.usecase.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.streams.toList


data class FollowUiState(
    val name: String,
    val following: Int,
    val follower: Int,
    val subscription: Int,
)

@HiltViewModel
class FollowViewModel @Inject constructor(
    val getFollowerUseCase: GetFollowerUseCase,
    val getFollowingUseCase: GetFollowingUseCase,
    val getProfileUserCase: GetProfileUseCase,
    val unFollowUseCase: UnFollowUseCase,
    val deleteUseCase: DeleteUseCase
) : ViewModel() {
    val following = MutableStateFlow<List<Follow>>(ArrayList())
    val follower = MutableStateFlow<List<Follow>>(ArrayList())
    val subscription = MutableStateFlow<List<Follow>>(ArrayList())
    val errorMessage = MutableStateFlow<String?>(null)
    val uiState = MutableStateFlow(
        FollowUiState(
            name = "",
            follower = 0,
            following = 0,
            subscription = 0
        )
    )

    fun clearErrorMessage() {
        viewModelScope.launch {
            errorMessage.emit(null)
        }
    }

    fun unFollow(id: Int) {
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke(id)
                following.update {
                    it.stream().filter { it.id != id }.toList()
                }
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }

    fun delete(id: Int) {
        Log.d("FollowViewModel", "id = ${id}")
        viewModelScope.launch {
            try {
                unFollowUseCase.invoke(id)
                follower.update {
                    it.stream().filter { it.id != id }.toList()
                }
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }

    init {
        viewModelScope.launch {
            try {
                follower.emit(getFollowerUseCase.invoke())
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
            try {
                following.emit(getFollowingUseCase.invoke())
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
            try {
                uiState.emit(getProfileUserCase.invoke())
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }
}