package com.sarang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sarang.torang.profile.DeleteUseCase
import com.sarang.torang.profile.GetFollowerUseCase
import com.sarang.torang.profile.GetFollowingUseCase
import com.sarang.torang.profile.GetMyProfileUseCase
import com.sarang.torang.profile.GetProfileUseCase
import com.sarang.torang.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OtherFollowViewModel @Inject constructor(
    private val getMyFollowerUseCase: GetFollowerUseCase,
    private val getMyFollowingUseCase: GetFollowingUseCase,
    private val getProfileUserCase: GetProfileUseCase,
    unFollowUseCase: UnFollowUseCase,
    deleteUseCase: DeleteUseCase
) : FollowViewModel(
    unFollowUseCase = unFollowUseCase,
    deleteUseCase = deleteUseCase
) {
    fun init(userId: Int) {
        Log.d("__OtherFollowViewModel", "load userId : ${userId}")
        viewModelScope.launch {
            try {
                follower.emit(getMyFollowerUseCase.invoke(userId))
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
            try {
                following.emit(getMyFollowingUseCase.invoke(userId))
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
            try {
                uiState.emit(getProfileUserCase.invoke(userId))
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }
}