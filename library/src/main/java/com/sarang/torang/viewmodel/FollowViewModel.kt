package com.sarang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.compose.follow.Follow
import com.sarang.torang.profile.DeleteUseCase
import com.sarang.torang.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class FollowUiState(
    val name: String,
    val following: Int,
    val follower: Int,
    val subscription: Int,
)

@HiltViewModel
open class FollowViewModel @Inject constructor(
    private val unFollowUseCase: UnFollowUseCase,
    private val deleteUseCase: DeleteUseCase
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
                deleteUseCase.invoke(id)
                follower.update {
                    it.stream().filter { it.id != id }.toList()
                }
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }
}