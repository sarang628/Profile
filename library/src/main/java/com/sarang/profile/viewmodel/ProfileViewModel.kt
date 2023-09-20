package com.sarang.profile.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sarang.profile.uistate.ProfileUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) :
    ViewModel() {

    val uiState = MutableStateFlow(ProfileUiState())

    fun loadProfile(id : Int) {
        viewModelScope.launch {
            val result = repository.loadProfile(id)
            uiState.emit(
                uiState.value.copy(
                    profileUrl = result.profilePictureUrl
                )
            )
        }
    }

//    val nothingProfile: LiveData<UserEntity> = userId.switchMap {
//        Logger.d("received user id : $it")
//        repository.loadProfile(it)
//    }

//    fun getUser(userId: Int): LiveData<UserEntity> {
//        this.userId.postValue(userId)
//        return repository.loadProfile(userId)
//    }

    /** 프로필 화면 사용자 정보 */
//    val my: LiveData<LoggedInUserEntity?> = repository.getMyProfile()

//    val myFeed: LiveData<List<FeedEntity>> = my.switchMap {
//        var data: LiveData<List<FeedEntity>> = MutableLiveData()
//        it?.let {
//            it.userId?.let {
//                data = repository.getMyFeed(it)
//            }
//        }
//        data
//    }

//    fun getFeed(userId: Int): LiveData<List<FeedEntity>> {
//        return repository.getMyFeed(userId)
//    }

//    val myFavorite: LiveData<List<FeedEntity>> = my.switchMap {
//        var data: LiveData<List<FeedEntity>> = MutableLiveData()
//        it?.let {
//            it.userId?.let {
//                data = repository.getMyFavorite(it)
//            }
//        }
//        data
//    }
}