package com.sarang.torang.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.Feed
import com.sarang.torang.usecase.profile.GetMyFeedUseCase
import com.sarang.torang.usecase.profile.LoadMyFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedListViewModel @Inject constructor(getMyFeedUseCase: GetMyFeedUseCase,
                                              loadMyFeedUseCase: LoadMyFeedUseCase) : ViewModel() {
    val list : StateFlow<List<Feed>> = getMyFeedUseCase.invoke()
        .stateIn(scope = viewModelScope,
                 started = SharingStarted.WhileSubscribed(5000),
                 initialValue = listOf())
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            loadMyFeedUseCase.invoke()
        }
    }
}