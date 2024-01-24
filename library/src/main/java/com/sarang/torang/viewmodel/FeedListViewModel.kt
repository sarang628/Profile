package com.sarang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.Feed
import com.sarang.torang.profile.GetFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedListViewModel @Inject constructor(
    private val getFeedUseCase: GetFeedUseCase
) : ViewModel() {

    private val _list = MutableStateFlow<List<Feed>>(arrayListOf())
    val list = _list.asStateFlow()
    val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun load(userId: Int) {
        viewModelScope.launch {
            try {
                _list.update {
                    getFeedUseCase.invoke(userId)
                }
            } catch (e: Exception) {
                Log.e("_FeedListViewModel", e.message.toString())
                _errorMessage.update {
                    e.message
                }
            }
        }
    }
}