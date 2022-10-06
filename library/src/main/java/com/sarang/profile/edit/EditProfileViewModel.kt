package com.sarang.profile.edit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sryang.torang_repository.repository.EditProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(private val repository: EditProfileRepository) :
    ViewModel() {
    //사용자 프로필 이미지 url
    val profileImageUrl = MutableLiveData<String>()
    var newProfileImageUrl: String? = null

    //사용자 이메일
    private val _email = MutableLiveData<String>()
    val email: LiveData<String> = _email

    //사용자 이름
    val name = MutableLiveData<String>()

    //저장소로부터 로그인된 사용자 불러오기
    val userData = repository.getUser()

    private val _completeEditProfile = MutableLiveData<Boolean>()
    val completeEditProfile: LiveData<Boolean> = _completeEditProfile

    private val _progress = MutableLiveData<Boolean>()
    val progress: LiveData<Boolean> = _progress

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun saveProfile() {
        viewModelScope.launch {
            _progress.postValue(true)
            delay(1000)
            try {
                repository.editProfile(name.value, newProfileImageUrl)
                _progress.postValue(false)
                _completeEditProfile.postValue(true)
            } catch (e: Exception) {
                _error.postValue(e.toString())
            }
            _progress.postValue(false)
        }
    }

    fun setUserName(name: String) {
        this.name.postValue(name)
    }

    fun setProfileImage(url: String) {
        profileImageUrl.postValue(url)
    }

    fun setNewProfileImage(url: String) {
        profileImageUrl.postValue(url)
        newProfileImageUrl = url
    }

    fun setEmail(email: String) {
        this._email.postValue(email)
    }
}