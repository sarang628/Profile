package com.sarang.torang.viewmodel

import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.DeleteUseCase
import com.sarang.torang.usecase.GetMyFollowerUseCase
import com.sarang.torang.usecase.GetMyFollowingUseCase
import com.sarang.torang.usecase.GetMyProfileUseCase
import com.sarang.torang.usecase.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyFollowViewModel @Inject constructor(
    private val getMyFollowerUseCase: GetMyFollowerUseCase,
    private val getMyFollowingUseCase: GetMyFollowingUseCase,
    private val getProfileUserCase: GetMyProfileUseCase,
    unFollowUseCase: UnFollowUseCase,
    deleteUseCase: DeleteUseCase
) : FollowViewModel(
    unFollowUseCase = unFollowUseCase,
    deleteUseCase = deleteUseCase
) {
    init {
        viewModelScope.launch {
            try {
                follower.emit(getMyFollowerUseCase.invoke())
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
            try {
                following.emit(getMyFollowingUseCase.invoke())
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