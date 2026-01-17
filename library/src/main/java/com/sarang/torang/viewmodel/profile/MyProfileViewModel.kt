package com.sarang.torang.viewmodel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.compose.profile.MyProfileUiState
import com.sarang.torang.usecase.profile.GetMyProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MyProfileViewModel @Inject constructor(
    myProfileUseCase    : GetMyProfileUseCase
) : ViewModel() {
    val uiState: StateFlow<MyProfileUiState> = myProfileUseCase.invoke()
                                                               .stateIn(scope = viewModelScope,
                                                                        started = SharingStarted.WhileSubscribed(5000),
                                                                        initialValue = MyProfileUiState.Loading)

    fun updateProfileImage(uri: String) {

    }
}