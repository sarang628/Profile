package com.sryang.myapplication

import com.sryang.torang_repository.data.dao.LoggedInUserDao
import com.sryang.torang_repository.data.dao.UserDao
import com.sryang.torang_repository.data.entity.LoggedInUserEntity
import com.sryang.torang_repository.data.entity.UserEntity
import com.sryang.torang_repository.repository.EditProfileRepository
import com.sryang.torang_repository.repository.EditProfileResponse
import com.sryang.torang_repository.services.RestaurantService
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class ProfileTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Inject
    lateinit var loggedInUserDao: LoggedInUserDao

    @Inject
    lateinit var userDao: UserDao

    @Inject
    lateinit var editProfileRepository: EditProfileRepository

    @Inject
    lateinit var restaurantService: RestaurantService

    suspend fun changeUserId(userId: Int) {
        loggedInUserDao.insert(
            LoggedInUserEntity(
                userId = userId,
                userName = "TORANG12"
            )
        )
    }

    fun hiltTest1() {
        runBlocking {
            userDao.insertAll(ArrayList<UserEntity>().apply {
                add(
                    UserEntity(
                        userId = 100
                    )
                )
            })
        }
    }

    /**
     * 프로필 저장소 업데이트 사용자 없음 테스트
     */
    @Test
    fun profileUpdateNoUserTest() {
        runBlocking {
            //사용자 Id 변경
            changeUserId(100)
            //API 호출
            val result = editProfileRepository.editProfile("test", null)
            //결과
            Assert.assertEquals(result, EditProfileResponse.NO_USER)
        }
    }

    /**
     * 프로필 저장소 업데이트 성공 테스트
     */
    @Test
    fun profileUpdateSuccessTest() {
        runBlocking {
            //사용자 Id 변경
            changeUserId(44)
            //API 호출
            val result = editProfileRepository.editProfile("test", null)
            //결과
            Assert.assertEquals(result, EditProfileResponse.SUCCESS)
        }
    }

}