package com.sarang.torang.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.Feed
import com.sarang.torang.usecase.GetMyFeedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyFeedListViewModel @Inject constructor(
    private val getMyFeedUseCase: GetMyFeedUseCase
) : ViewModel() {

    private val _list = MutableStateFlow<List<Feed>>(arrayListOf())
    val list = _list.asStateFlow()
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    fun load(userId: Int) {
        viewModelScope.launch {
            try {
                _list.update {
                    getMyFeedUseCase.invoke(userId)
                }
            } catch (e: Exception) {
                Log.e("__FeedListViewModel", e.message.toString())
                _errorMessage.update {
                    e.message
                }
            }
        }
    }
}