package com.sarang.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.example.torang_core.data.model.Feed
import com.example.torang_core.data.model.LoggedInUserData
import com.example.torang_core.data.model.UserData
import com.example.torang_core.repository.ProfileRepository
import com.example.torang_core.util.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository: ProfileRepository) :
    ViewModel() {

    private val userId = MutableLiveData<Int>()

    val nothingProfile: LiveData<UserData> = userId.switchMap {
        Logger.d("received user id : $it")
        repository.loadProfile(it)
    }

    fun getUser(userId: Int): LiveData<UserData> {
        this.userId.postValue(userId)
        return repository.loadProfile(userId)
    }

    /** 프로필 화면 사용자 정보 */
    val my: LiveData<LoggedInUserData?> = repository.getMyProfile()

    val myFeed: LiveData<List<Feed>> = my.switchMap {
        var data: LiveData<List<Feed>> = MutableLiveData()
        it?.let {
            it.userId?.let {
                data = repository.getMyFeed(it)
            }
        }
        data
    }

    fun getFeed(userId: Int): LiveData<List<Feed>> {
        return repository.getMyFeed(userId)
    }

    val myFavorite: LiveData<List<Feed>> = my.switchMap {
        var data: LiveData<List<Feed>> = MutableLiveData()
        it?.let {
            it.userId?.let {
                data = repository.getMyFavorite(it)
            }
        }
        data
    }
}