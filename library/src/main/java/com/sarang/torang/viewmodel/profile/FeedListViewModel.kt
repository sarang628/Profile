package com.sarang.torang.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.profile.Feed
import com.sarang.torang.data.profile.FeedListItemUIState
import com.sarang.torang.usecase.profile.GetFeedByUserIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    val getMyFeedUseCase: GetFeedByUserIdUseCase
) : ViewModel() {

    private val _list = MutableStateFlow<List<FeedListItemUIState>>(arrayListOf())
    val list : StateFlow<List<FeedListItemUIState>> = _list.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun load(userId : Int) {
        viewModelScope.launch {
            try {
                _list.value = getMyFeedUseCase.invoke(userId)
            } catch (e: Exception) {
                Log.e("__FeedListViewModel", e.message.toString())
                _errorMessage.update {
                    e.message
                }
            }
        }
    }

}