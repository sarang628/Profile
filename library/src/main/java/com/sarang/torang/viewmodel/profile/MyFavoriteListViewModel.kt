package com.sarang.torang.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.data.profile.Feed
import com.sarang.torang.usecase.profile.FineMyFavoriteFlowUseCase
import com.sarang.torang.usecase.profile.GetMyFeedUseCase
import com.sarang.torang.usecase.profile.LoadMyFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyFavoriteItemUiState(
    val reviewId : Int,
    val reviewImage : String
)

@HiltViewModel
class MyFavoriteListViewModel @Inject constructor(fineMyFavoriteFlowUseCase: FineMyFavoriteFlowUseCase,
                                                  loadMyFavorite: LoadMyFavoriteUseCase
) : ViewModel() {
    val tag = "__MyFavoriteListViewModel"
    val list : StateFlow<List<MyFavoriteItemUiState>> = fineMyFavoriteFlowUseCase.invoke()
        .stateIn(scope = viewModelScope,
                 started = SharingStarted.Companion.WhileSubscribed(5000),
                 initialValue = listOf())
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                loadMyFavorite.invoke()
            }catch (e : Exception){
                Log.e("__MyFavoriteListViewModel", e.message.toString())
                _errorMessage.value += e.message.toString()
            }
        }
    }
}