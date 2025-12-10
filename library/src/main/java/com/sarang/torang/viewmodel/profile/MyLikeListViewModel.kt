package com.sarang.torang.viewmodel.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.torang.usecase.profile.FineMyLikeFlowUseCase
import com.sarang.torang.usecase.profile.LoadMyLikeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MyLikeListViewUiState(
    val reviewId : Int,
    val reviewImage : String
)

@HiltViewModel
class MyLikeListViewModel @Inject constructor(findMyLikeFlowUseCase: FineMyLikeFlowUseCase,
                                              loadMyLikeUseCase: LoadMyLikeUseCase
) : ViewModel() {
    val tag = "__MyFavoriteListViewModel"
    val list : StateFlow<List<MyLikeListViewUiState>> = findMyLikeFlowUseCase.invoke()
        .stateIn(scope = viewModelScope,
                 started = SharingStarted.WhileSubscribed(5000),
                 initialValue = listOf())
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                loadMyLikeUseCase.invoke()
            }catch (e : Exception){
                Log.e("MyLikeListViewModel", e.message.toString())
                _errorMessage.value += e.message.toString()
            }
        }
    }
}