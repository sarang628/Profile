package com.sarang.torang.viewmodel

import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.profile.DeleteUseCase
import com.sarang.torang.usecase.profile.GetMyFollowerUseCase
import com.sarang.torang.usecase.profile.GetMyFollowingUseCase
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import com.sarang.torang.usecase.profile.GetProfileUseCase
import com.sarang.torang.usecase.profile.UnFollowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyFollowViewModel @Inject constructor(
    private val getMyFollowerUseCase: GetMyFollowerUseCase,
    private val getMyFollowingUseCase: GetMyFollowingUseCase,
    private val getProfileUseCase: GetProfileUseCase,
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
                //
            } catch (e: Exception) {
                errorMessage.emit(e.toString())
            }
        }
    }
}